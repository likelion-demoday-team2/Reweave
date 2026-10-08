package com.reweave.backend.domain.purposegroup.dto;

import com.reweave.backend.domain.purposegroup.entity.PurposeGroupStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PurposeGroupCreateRequest(
        @NotBlank(message = "목적 그룹 이름을 입력해주세요.")
        String purposeName,

        @NotNull(message = "진행 상태를 입력해주세요.")
        PurposeGroupStatus status,

        Long categoryId
) {
}
