package se.issuetrackingsystem.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterRequest {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    private final String role;
}