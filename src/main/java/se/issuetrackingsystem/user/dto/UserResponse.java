package se.issuetrackingsystem.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import se.issuetrackingsystem.user.domain.User;

@Getter
public class UserResponse {

    private final Long userId;
    private final String username;
    private final String role;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }

}
