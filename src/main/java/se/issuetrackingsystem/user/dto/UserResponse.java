package se.issuetrackingsystem.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import se.issuetrackingsystem.user.domain.User;

@Getter
public class UserResponse {

    @NotBlank
    private final Long userId;

    @NotBlank
    private final String username;

    @NotBlank
    private final String role;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }

}
