package com.reweave.backend.domain.purposegroup.dto;

import jakarta.validation.constraints.NotBlank;

public record PurposeGroupUpdateRequest(
        @NotBlank(message = "수정할 목적 그룹 이름을 입력해주세요.")
        String purposeName
) {
}
