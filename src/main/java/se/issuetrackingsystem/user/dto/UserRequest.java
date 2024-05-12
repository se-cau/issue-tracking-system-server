package se.issuetrackingsystem.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;
}