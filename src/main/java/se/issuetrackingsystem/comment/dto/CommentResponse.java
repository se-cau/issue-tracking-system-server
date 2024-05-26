package se.issuetrackingsystem.comment.dto;

import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.comment.domain.Comment;
import se.issuetrackingsystem.user.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {
    private Long id;

    private String message;

    private LocalDateTime created_at;

    private Long authorid;

    private String username;

    private String role;
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        if (comment.getAuthor() != null) {
            this.authorid = comment.getAuthor().getId();
            this.username = comment.getAuthor().getUsername();
            this.role = comment.getAuthor().getRole();
        }
        this.created_at = comment.getCreated_at();
    }
}
