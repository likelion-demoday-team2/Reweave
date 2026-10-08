package com.reweave.backend.domain.purposegroup.dto;

import com.reweave.backend.domain.purposegroup.entity.PurposeGroup;
import com.reweave.backend.domain.purposegroup.entity.PurposeGroupStatus;

import java.time.LocalDateTime;

public record PurposeGroupResponse(
        Long purposeGroupId,
        Long userId,
        Long categoryId,
        String purposeName,
        PurposeGroupStatus status,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {

    public static PurposeGroupResponse from(PurposeGroup purposeGroup) {
        return new PurposeGroupResponse(
                purposeGroup.getId(),
                purposeGroup.getUserId(),
                purposeGroup.getCategoryId(),
                purposeGroup.getPurposeName(),
                purposeGroup.getStatus(),
                purposeGroup.getCreatedDate(),
                purposeGroup.getModifiedDate()
        );
    }
}
