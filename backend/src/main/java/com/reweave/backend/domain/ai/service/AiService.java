package com.reweave.backend.domain.ai.service;

import com.reweave.backend.domain.ai.dto.AiRecommendRequest;
import com.reweave.backend.domain.ai.dto.AiRecommendResponse;
import com.reweave.backend.domain.bookmark.entity.Bookmark;
import com.reweave.backend.domain.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final BookmarkRepository bookmarkRepository;
    private final WebClient webClient = WebClient.create();

    public AiRecommendResponse recommendBookmarksForPurpose(Long userId, AiRecommendRequest request) {
        // 1. 유저가 선택한 카테고리 내의 삭제되지 않은 북마크만 DB에서 1차 필터링 조회
        List<Bookmark> targetBookmarks = bookmarkRepository.findByUserIdAndCategoryIdInAndDeletedDateIsNull(userId, request.categoryIds());

        // 선택한 카테고리에 저장된 북마크가 없는 경우
        if (targetBookmarks.isEmpty()) {
            return new AiRecommendResponse(List.of(), "선택하신 카테고리에 저장된 북마크가 없습니다.");
        }

        // 2. AI에 넘겨줄 북마크 목록 데이터 구성 (ID, 제목, 요약)
        String bookmarkDataText = targetBookmarks.stream()
                .map(b -> String.format("[ID: %d] 제목: %s, 설명: %s", b.getId(), b.getTitle(), b.getSummary()))
                .collect(Collectors.joining("\n"));

        // 3. Gemini Prompt 구성
        String prompt = String.format("""
                당신은 프로젝트 목적에 맞는 북마크를 매칭해주는 AI 컨설턴트입니다.
                
                프로젝트 제목: '%s'
                
                후보 북마크 목록:
                %s
                
                위 후보 북마크 목록 중에서 프로젝트 제목('%s')과 관련성이 높은 북마크의 ID들을 선택해 주세요.
                
                [응답 형식]
                추천 ID: [ID1, ID2, ...] (없다면 [NONE])
                이유: 2줄 이내의 간단한 한국어 설명
                """, request.purposeTitle(), bookmarkDataText, request.purposeTitle());

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        // 4. Gemini API 호출
        try {
            Map response = webClient.post()
                    .uri(apiUrl + "?key=" + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            String rawResult = parseGeminiResponse(response);
            List<Long> matchedIds = extractBookmarkIds(rawResult);

            return new AiRecommendResponse(matchedIds, rawResult);

        } catch (WebClientResponseException e) {
            // 외부 API 에러 로그 보완
            System.err.println("Gemini API 호출 실패 (Status Code: " + e.getStatusCode() + ")");
            System.err.println("Gemini API Error Body: " + e.getResponseBodyAsString());
            e.printStackTrace();
            return new AiRecommendResponse(List.of(), "AI API 호출 실패: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return new AiRecommendResponse(List.of(), "AI 연동 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    private String parseGeminiResponse(Map response) {
        try {
            List candidates = (List) response.get("candidates");
            Map firstCandidate = (Map) candidates.get(0);
            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");
            Map firstPart = (Map) parts.get(0);
            return (String) firstPart.get("text");
        } catch (Exception e) {
            return "AI 응답 분석 중 오류가 발생했습니다.";
        }
    }

    private List<Long> extractBookmarkIds(String text) {
        List<Long> ids = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String idGroup = matcher.group(1);
            String[] split = idGroup.split(",");
            for (String s : split) {
                try {
                    ids.add(Long.parseLong(s.trim()));
                } catch (NumberFormatException ignored) {}
            }
        }
        return ids;
    }
}