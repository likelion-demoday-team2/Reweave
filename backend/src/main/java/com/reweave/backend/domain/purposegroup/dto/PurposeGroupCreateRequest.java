package com.reweave.backend.domain.purposegroup.dto;

public record PurposeGroupCreateRequest(
        Long userId,
        String purposeName,
        String status
) {
}
