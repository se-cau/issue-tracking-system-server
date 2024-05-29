package se.issuetrackingsystem.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username은 공백이 될 수 없습니다.")
    private final String username;

    @NotBlank(message = "Password는 공백이 될 수 없습니다.")
    private final String password;

    @NotBlank(message = "Role은 공백이 될 수 없습니다.")
    private final String role;
}