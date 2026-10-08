package com.reweave.backend.domain.bookmark.repository;

import com.reweave.backend.domain.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // 삭제 안 된 내 북마크 1개
    @EntityGraph(attributePaths = "category")
    Optional<Bookmark> findByIdAndUserIdAndDeletedDateIsNull(Long id, Long userId);

    // 삭제 여부 상관없이 내 북마크 1개 (영구 삭제용)
    Optional<Bookmark> findByIdAndUserId(Long id, Long userId);

    // 중복 확인 (휴지통 포함)
    Optional<Bookmark> findByUserIdAndUrlHash(Long userId, String urlHash);

    // 목록: 전체 / 카테고리별 / 미분류
    @EntityGraph(attributePaths = "category")
    Page<Bookmark> findByUserIdAndDeletedDateIsNull(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = "category")
    Page<Bookmark> findByUserIdAndCategoryIdAndDeletedDateIsNull(Long userId, Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = "category")
    Page<Bookmark> findByUserIdAndCategoryIsNullAndDeletedDateIsNull(Long userId, Pageable pageable);

    // 최근 삭제된 링크
    @EntityGraph(attributePaths = "category")
    Page<Bookmark> findByUserIdAndDeletedDateIsNotNull(Long userId, Pageable pageable);

    // 여러 개 처리용
    List<Bookmark> findAllByIdInAndUserIdAndDeletedDateIsNull(Collection<Long> ids, Long userId);

    List<Bookmark> findAllByIdInAndUserId(Collection<Long> ids, Long userId);

    // 검색: 제목·요약·카테고리명 부분 일치
    @Query(value = """
            select b from Bookmark b left join fetch b.category c
            where b.user.id = :userId
              and b.deletedDate is null
              and (:categoryId is null or c.id = :categoryId)
              and (lower(b.title) like lower(concat('%', :keyword, '%'))
                or lower(b.summary) like lower(concat('%', :keyword, '%'))
                or lower(c.categoryName) like lower(concat('%', :keyword, '%')))
            """,
            countQuery = """
            select count(b) from Bookmark b left join b.category c
            where b.user.id = :userId
              and b.deletedDate is null
              and (:categoryId is null or c.id = :categoryId)
              and (lower(b.title) like lower(concat('%', :keyword, '%'))
                or lower(b.summary) like lower(concat('%', :keyword, '%'))
                or lower(c.categoryName) like lower(concat('%', :keyword, '%')))
            """)
    Page<Bookmark> search(@Param("userId") Long userId,
                          @Param("categoryId") Long categoryId,
                          @Param("keyword") String keyword,
                          Pageable pageable);
}