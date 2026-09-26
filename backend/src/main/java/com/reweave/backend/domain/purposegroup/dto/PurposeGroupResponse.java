package com.reweave.backend.domain.purposegroup.dto;

import com.reweave.backend.domain.purposegroup.entity.PurposeGroup;

import java.time.LocalDateTime;

public record PurposeGroupResponse(
        Long purposeGroupId,
        Long userId,
        String purposeName,
        String status,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {

    public static PurposeGroupResponse from(PurposeGroup purposeGroup) {
        return new PurposeGroupResponse(
                purposeGroup.getId(),
                purposeGroup.getUserId(),
                purposeGroup.getPurposeName(),
                purposeGroup.getStatus(),
                purposeGroup.getCreatedDate(),
                purposeGroup.getModifiedDate()
        );
    }
}
