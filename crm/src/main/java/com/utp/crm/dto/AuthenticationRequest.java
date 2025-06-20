package com.utp.crm.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
