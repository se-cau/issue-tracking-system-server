package se.issuetrackingsystem.issue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.issue.domain.Issue;

@Getter
@Setter
public class IssueRequest {

    @NotBlank(message = "Title은 공백이 될 수 없습니다.")
    private String title;

    private String description;

    private Issue.Priority priority;

    private Issue.Status status;

    private Long userId;

    private Long assigneeId;

}
