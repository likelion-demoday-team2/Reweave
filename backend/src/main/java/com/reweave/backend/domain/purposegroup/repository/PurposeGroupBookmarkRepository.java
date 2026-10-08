package com.reweave.backend.domain.purposegroup.repository;

import com.reweave.backend.domain.purposegroup.entity.PurposeGroupBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PurposeGroupBookmarkRepository extends JpaRepository<PurposeGroupBookmark, Long> {

    // 목적 그룹 내 삭제 안 된 북마크 목록 페이징 조회
    @EntityGraph(attributePaths = {"bookmark", "bookmark.category"})
    Page<PurposeGroupBookmark> findByPurposeGroupIdAndBookmarkDeletedDateIsNull(Long purposeGroupId, Pageable pageable);

    // 해당 목적 그룹에 이미 매핑된 북마크 ID 목록 한 번에 조회
    @Query("SELECT pgb.bookmark.id FROM PurposeGroupBookmark pgb WHERE pgb.purposeGroup.id = :purposeGroupId")
    Set<Long> findBookmarkIdsByPurposeGroupId(@Param("purposeGroupId") Long purposeGroupId);

    // 목적 그룹 삭제 시 연관 매핑 데이터 일괄 삭제
    void deleteAllByPurposeGroupId(Long purposeGroupId);
}
