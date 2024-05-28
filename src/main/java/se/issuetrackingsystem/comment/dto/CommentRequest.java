package se.issuetrackingsystem.comment.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    @NotBlank
    private String message;

    @NotBlank
    private Long authorId;
}
