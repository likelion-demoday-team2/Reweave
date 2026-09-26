package com.reweave.backend.domain.purposegroup.controller;

import com.reweave.backend.domain.purposegroup.dto.PurposeGroupCreateRequest;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupResponse;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupStatusRequest;
import com.reweave.backend.domain.purposegroup.dto.PurposeGroupUpdateRequest;
import com.reweave.backend.domain.purposegroup.service.PurposeGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purpose-groups")
public class PurposeGroupController {

    private final PurposeGroupService purposeGroupService;

    public PurposeGroupController(PurposeGroupService purposeGroupService) {
        this.purposeGroupService = purposeGroupService;
    }

    // 목적 그룹 생성
    @PostMapping
    public ResponseEntity<PurposeGroupResponse> create(
            @RequestBody PurposeGroupCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(purposeGroupService.create(request));
    }

    // 목적 그룹 목록 조회
    @GetMapping
    public ResponseEntity<List<PurposeGroupResponse>> findAll(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(
                purposeGroupService.findAll(userId)
        );
    }

    // 목적 그룹 상세 조회
    @GetMapping("/{purposeGroupId}")
    public ResponseEntity<PurposeGroupResponse> findById(
            @PathVariable Long purposeGroupId,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(
                purposeGroupService.findById(userId, purposeGroupId)
        );
    }

    // 목적 그룹 이름 수정
    @PatchMapping("/{purposeGroupId}")
    public ResponseEntity<PurposeGroupResponse> update(
            @PathVariable Long purposeGroupId,
            @RequestParam Long userId,
            @RequestBody PurposeGroupUpdateRequest request
    ) {
        return ResponseEntity.ok(
                purposeGroupService.update(
                        userId,
                        purposeGroupId,
                        request
                )
        );
    }

    // 목적 그룹 진행 상태 변경
    @PatchMapping("/{purposeGroupId}/status")
    public ResponseEntity<PurposeGroupResponse> updateStatus(
            @PathVariable Long purposeGroupId,
            @RequestParam Long userId,
            @RequestBody PurposeGroupStatusRequest request
    ) {
        return ResponseEntity.ok(
                purposeGroupService.updateStatus(
                        userId,
                        purposeGroupId,
                        request
                )
        );
    }

    // 목적 그룹 삭제
    @DeleteMapping("/{purposeGroupId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long purposeGroupId,
            @RequestParam Long userId
    ) {
        purposeGroupService.delete(userId, purposeGroupId);

        return ResponseEntity.noContent().build();
    }
}