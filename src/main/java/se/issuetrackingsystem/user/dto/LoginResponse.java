package se.issuetrackingsystem.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import se.issuetrackingsystem.user.domain.User;

@Getter
public class LoginResponse {

    @NotBlank
    private final String username;

    public LoginResponse(User user) {
        this.username = user.getUsername();
    }
}