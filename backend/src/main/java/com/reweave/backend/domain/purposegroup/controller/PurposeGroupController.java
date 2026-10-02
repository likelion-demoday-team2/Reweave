package com.reweave.backend.domain.purposegroup.controller;

import com.reweave.backend.domain.purposegroup.dto.PurposeGroupCreateRequest;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupResponse;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupStatusRequest;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupUpdateRequest;
import com.reweave.backend.domain.purposegroup.service.PurposeGroupService;
import com.reweave.backend.global.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purpose-groups")
public class PurposeGroupController {

    private final PurposeGroupService purposeGroupService;

    public PurposeGroupController(PurposeGroupService purposeGroupService) {
        this.purposeGroupService = purposeGroupService;
    }

    // 목적 그룹 생성
    @PostMapping
    public ResponseEntity<ApiResponse<PurposeGroupResponse>> create(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody PurposeGroupCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(purposeGroupService.create(userId, request)));
    }

    // 목적 그룹 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<PurposeGroupResponse>>> findAll(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(purposeGroupService.findAll(userId)));
    }

    // 목적 그룹 상세 조회
    @GetMapping("/{purposeGroupId}")
    public ResponseEntity<ApiResponse<PurposeGroupResponse>> findById(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long purposeGroupId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(purposeGroupService.findById(userId, purposeGroupId)));
    }

    // 목적 그룹 이름 수정
    @PatchMapping("/{purposeGroupId}")
    public ResponseEntity<ApiResponse<PurposeGroupResponse>> update(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long purposeGroupId,
            @Valid @RequestBody PurposeGroupUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(purposeGroupService.update(userId, purposeGroupId, request)));
    }

    // 목적 그룹 진행 상태 변경
    @PatchMapping("/{purposeGroupId}/status")
    public ResponseEntity<ApiResponse<PurposeGroupResponse>> updateStatus(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long purposeGroupId,
            @Valid @RequestBody PurposeGroupStatusRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(purposeGroupService.updateStatus(userId, purposeGroupId, request)));
    }

    // 목적 그룹 삭제
    @DeleteMapping("/{purposeGroupId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long purposeGroupId
    ) {
        purposeGroupService.delete(userId, purposeGroupId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}