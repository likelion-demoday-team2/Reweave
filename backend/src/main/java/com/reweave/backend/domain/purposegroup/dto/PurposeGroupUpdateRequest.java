package com.reweave.backend.domain.purposegroup.dto;

import jakarta.validation.constraints.NotBlank;

public record PurposeGroupUpdateRequest(
        @NotBlank(message = "목적 그룹 이름은 필수입니다.")
        String purposeName,

        Long categoryId
) {
}
