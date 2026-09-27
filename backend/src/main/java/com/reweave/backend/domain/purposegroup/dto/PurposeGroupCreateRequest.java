package com.reweave.backend.domain.purposegroup.dto;

import jakarta.validation.constraints.NotBlank;

public record PurposeGroupCreateRequest(
        @NotBlank(message = "목적 그룹 이름을 입력해주세요.")
        String purposeName,

        @NotBlank(message = "진행 상태를 입력해주세요.")
        String status,

        Long categoryId
) {
}
