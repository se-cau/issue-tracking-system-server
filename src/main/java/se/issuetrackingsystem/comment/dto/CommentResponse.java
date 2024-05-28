package se.issuetrackingsystem.comment.dto;

import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.comment.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {

    private Long id;

    private String message;

    private LocalDateTime createdAt;

    private Long authorId;

    private String username;

    private String role;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        if (comment.getAuthor() != null) {
            this.authorId = comment.getAuthor().getId();
            this.username = comment.getAuthor().getUsername();
            this.role = comment.getAuthor().getRole();
        }
        this.createdAt = comment.getCreatedAt();
    }
}
