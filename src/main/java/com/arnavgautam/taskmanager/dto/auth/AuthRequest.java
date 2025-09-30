package com.arnavgautam.taskmanager.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication request")
public class AuthRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "Username or email", example = "johndoe")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password", example = "SecurePassword123!")
    private String password;
}
