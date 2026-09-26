package com.reweave.backend.domain.purposegroup.service;

import com.reweave.backend.domain.purposegroup.dto.PurposeGroupCreateRequest;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupResponse;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupStatusRequest;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupUpdateRequest;
import com.reweave.backend.domain.purposegroup.entity.PurposeGroup;
import com.reweave.backend.domain.purposegroup.repository.PurposeGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PurposeGroupService {

    private final PurposeGroupRepository purposeGroupRepository;

    public PurposeGroupService(PurposeGroupRepository purposeGroupRepository) {
        this.purposeGroupRepository = purposeGroupRepository;
    }

    // 목적 그룹 생성
    @Transactional
    public PurposeGroupResponse create(PurposeGroupCreateRequest request) {

        PurposeGroup purposeGroup = new PurposeGroup(
                request.userId(),
                request.purposeName(),
                request.status()
        );

        PurposeGroup savedPurposeGroup =
                purposeGroupRepository.save(purposeGroup);

        return PurposeGroupResponse.from(savedPurposeGroup);
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

        PurposeGroup purposeGroup = purposeGroupRepository
                .findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 목적 그룹입니다.")
                );

        return PurposeGroupResponse.from(purposeGroup);
    }

    // 목적 그룹 이름 수정
    @Transactional
    public PurposeGroupResponse update(
            Long userId,
            Long purposeGroupId,
            PurposeGroupUpdateRequest request
    ) {

        PurposeGroup purposeGroup = purposeGroupRepository
                .findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 목적 그룹입니다.")
                );

        purposeGroup.update(request.purposeName());

        return PurposeGroupResponse.from(purposeGroup);
    }

    // 진행 상태 변경
    @Transactional
    public PurposeGroupResponse updateStatus(
            Long userId,
            Long purposeGroupId,
            PurposeGroupStatusRequest request
    ) {

        PurposeGroup purposeGroup = purposeGroupRepository
                .findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 목적 그룹입니다.")
                );

        purposeGroup.updateStatus(request.status());

        return PurposeGroupResponse.from(purposeGroup);
    }

    // 목적 그룹 삭제
    @Transactional
    public void delete(Long userId, Long purposeGroupId) {

        PurposeGroup purposeGroup = purposeGroupRepository
                .findByIdAndUserId(purposeGroupId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 목적 그룹입니다.")
                );

        purposeGroupRepository.delete(purposeGroup);
    }
}