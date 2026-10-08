package com.reweave.backend.domain.purposegroup.service;

import com.reweave.backend.domain.category.repository.CategoryRepository;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupCreateRequest;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupResponse;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupStatusRequest;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupUpdateRequest;
import com.reweave.backend.domain.purposegroup.entity.PurposeGroup;
import com.reweave.backend.domain.purposegroup.repository.PurposeGroupRepository;
import com.reweave.backend.global.exception.CustomException;
import com.reweave.backend.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PurposeGroupService {

    private final PurposeGroupRepository purposeGroupRepository;
    private final CategoryRepository categoryRepository;

    public PurposeGroupService(PurposeGroupRepository purposeGroupRepository, CategoryRepository categoryRepository) {
        this.purposeGroupRepository = purposeGroupRepository;
        this.categoryRepository = categoryRepository;
    }

    // 목적 그룹 생성
    @Transactional
    public PurposeGroupResponse create(Long userId, PurposeGroupCreateRequest request) {
        // categoryId가 존재할 때만 카테고리 유효성 검증
        if (request.categoryId() != null) {
            validateCategoryExists(request.categoryId());
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
            validateCategoryExists(request.categoryId());
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

    @Transactional
    public void delete(Long userId, Long purposeGroupId) {
        PurposeGroup purposeGroup = purposeGroupRepository.findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PURPOSE_GROUP_NOT_FOUND));

        purposeGroupRepository.delete(purposeGroup);
    }

    private void validateCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }
    }
}