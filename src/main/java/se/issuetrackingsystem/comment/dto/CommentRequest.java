package se.issuetrackingsystem.comment.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    @NotBlank(message = "Message는 공백이 될 수 없습니다.")
    private String message;

    private Long authorId;
}