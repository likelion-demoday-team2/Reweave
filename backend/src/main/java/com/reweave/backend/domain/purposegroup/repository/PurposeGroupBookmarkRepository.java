package com.reweave.backend.domain.purposegroup.repository;

import com.reweave.backend.domain.purposegroup.entity.PurposeGroupBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurposeGroupBookmarkRepository extends JpaRepository<PurposeGroupBookmark, Long> {

    // 목적 그룹 내 삭제 안 된 북마크 목록 페이징 조회
    @EntityGraph(attributePaths = {"bookmark", "bookmark.category"})
    Page<PurposeGroupBookmark> findByPurposeGroupIdAndBookmarkDeletedDateIsNull(Long purposeGroupId, Pageable pageable);

    // 중복 등록 여부 확인
    boolean existsByPurposeGroupIdAndBookmarkId(Long purposeGroupId, Long bookmarkId);
}
