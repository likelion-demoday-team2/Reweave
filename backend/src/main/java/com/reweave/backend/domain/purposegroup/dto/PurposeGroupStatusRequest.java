package com.reweave.backend.domain.purposegroup.dto;

import jakarta.validation.constraints.NotBlank;

public record PurposeGroupStatusRequest(
        @NotBlank(message = "변경할 진행 상태를 입력해주세요.")
        String status
) {
}
