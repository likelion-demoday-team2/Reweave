package com.reweave.backend.domain.purposegroup.service;

import com.reweave.backend.domain.bookmark.entity.Bookmark;
import com.reweave.backend.domain.bookmark.repository.BookmarkRepository;
import com.reweave.backend.domain.category.repository.CategoryRepository;
import com.reweave.backend.domain.purposegroup.dto.*;
import com.reweave.backend.domain.purposegroup.entity.PurposeGroup;
import com.reweave.backend.domain.purposegroup.entity.PurposeGroupBookmark;
import com.reweave.backend.domain.purposegroup.repository.PurposeGroupBookmarkRepository;
import com.reweave.backend.domain.purposegroup.repository.PurposeGroupRepository;
import com.reweave.backend.global.exception.CustomException;
import com.reweave.backend.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PurposeGroupService {

    private final PurposeGroupRepository purposeGroupRepository;
    private final CategoryRepository categoryRepository;
    private final PurposeGroupBookmarkRepository purposeGroupBookmarkRepository;
    private final BookmarkRepository bookmarkRepository;

    public PurposeGroupService(PurposeGroupRepository purposeGroupRepository,
                               CategoryRepository categoryRepository,
                               PurposeGroupBookmarkRepository purposeGroupBookmarkRepository,
                               BookmarkRepository bookmarkRepository) {
        this.purposeGroupRepository = purposeGroupRepository;
        this.categoryRepository = categoryRepository;
        this.purposeGroupBookmarkRepository = purposeGroupBookmarkRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    // 목적 그룹 생성
    @Transactional
    public PurposeGroupResponse create(Long userId, PurposeGroupCreateRequest request) {
        if (request.categoryId() != null) {
            validateCategoryOwnership(request.categoryId(), userId);
        }

        PurposeGroup purposeGroup = new PurposeGroup(
                userId,
                request.purposeName().trim(),
                request.status(),
                request.categoryId()
        );
        return PurposeGroupResponse.from(purposeGroupRepository.save(purposeGroup));
    }

    // 목적 그룹 목록 조회
    public List<PurposeGroupResponse> findAll(Long userId) {
        return purposeGroupRepository.findAllByUserId(userId)
                .stream()
                .map(PurposeGroupResponse::from)
                .toList();
    }

    // 목적 그룹 상세 조회
    public PurposeGroupResponse findById(Long userId, Long purposeGroupId) {
        PurposeGroup purposeGroup = purposeGroupRepository.findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PURPOSE_GROUP_NOT_FOUND));

        return PurposeGroupResponse.from(purposeGroup);
    }

    // 목적 그룹 이름 수정
    @Transactional
    public PurposeGroupResponse update(Long userId, Long purposeGroupId, PurposeGroupUpdateRequest request) {
        if (request.categoryId() != null) {
            validateCategoryOwnership(request.categoryId(), userId);
        }

        PurposeGroup purposeGroup = purposeGroupRepository.findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PURPOSE_GROUP_NOT_FOUND));

        purposeGroup.update(request.purposeName().trim(), request.categoryId());
        purposeGroupRepository.flush();
        return PurposeGroupResponse.from(purposeGroup);
    }

    // 진행 상태 변경
    @Transactional
    public PurposeGroupResponse updateStatus(Long userId, Long purposeGroupId, PurposeGroupStatusRequest request) {
        PurposeGroup purposeGroup = purposeGroupRepository.findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PURPOSE_GROUP_NOT_FOUND));

        purposeGroup.updateStatus(request.status());
        purposeGroupRepository.flush();
        return PurposeGroupResponse.from(purposeGroup);
    }

    // 목적 그룹 삭제
    @Transactional
    public void delete(Long userId, Long purposeGroupId) {
        PurposeGroup purposeGroup = purposeGroupRepository.findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PURPOSE_GROUP_NOT_FOUND));

        purposeGroupRepository.delete(purposeGroup);
    }

    private void validateCategoryOwnership(Long categoryId, Long userId) {
        categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    // 목적 그룹에 북마크 추가
    @Transactional
    public void addBookmarks(Long userId, Long purposeGroupId, PurposeGroupBookmarkAddRequest request) {
        PurposeGroup purposeGroup = purposeGroupRepository.findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PURPOSE_GROUP_NOT_FOUND));

        List<Bookmark> bookmarks = bookmarkRepository.findAllByIdInAndUserIdAndDeletedDateIsNull(
                request.bookmarkIds(), userId
        );

        if (bookmarks.size() != request.bookmarkIds().size()) {
            throw new CustomException(ErrorCode.BOOKMARK_NOT_FOUND);
        }

        List<PurposeGroupBookmark> newMappings = bookmarks.stream()
                .filter(b -> !purposeGroupBookmarkRepository.existsByPurposeGroupIdAndBookmarkId(purposeGroupId, b.getId()))
                .map(b -> new PurposeGroupBookmark(purposeGroup, b))
                .toList();

        purposeGroupBookmarkRepository.saveAll(newMappings);
    }

    // 목적 그룹 내 북마크 목록 페이징 조회
    public Page<PurposeGroupBookmarkResponse> findBookmarks(Long userId, Long purposeGroupId, Pageable pageable) {
        if (!purposeGroupRepository.existsByIdAndUserId(purposeGroupId, userId)) {
            throw new CustomException(ErrorCode.PURPOSE_GROUP_NOT_FOUND);
        }

        return purposeGroupBookmarkRepository
                .findByPurposeGroupIdAndBookmarkDeletedDateIsNull(purposeGroupId, pageable)
                .map(pgb -> PurposeGroupBookmarkResponse.from(pgb.getBookmark()));
    }
}