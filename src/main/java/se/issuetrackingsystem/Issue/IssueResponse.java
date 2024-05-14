package se.issuetrackingsystem.Issue;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IssueResponse {
    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    private String reporter;

    private String assignee;

    private String fixer;

    private Long number;

    private Issue.Status status;

    private Issue.Priority priority;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
