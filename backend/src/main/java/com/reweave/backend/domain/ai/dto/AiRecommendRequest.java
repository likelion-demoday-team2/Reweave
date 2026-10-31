package com.reweave.backend.domain.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record AiRecommendRequest(
        @Schema(description = "유저가 입력한 프로젝트/목적 제목", example = "백엔드 개발 스터디")
        @NotBlank(message = "목적 제목을 입력해주세요.")
        String purposeTitle,

        @Schema(description = "유저가 선택한 참조 카테고리 ID 목록", example = "[1, 2]")
        @NotEmpty(message = "최소 하나 이상의 참조 카테고리를 선택해주세요.")
        List<Long> categoryIds
) {}