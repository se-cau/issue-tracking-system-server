package se.issuetrackingsystem.issue.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.issue.domain.Issue;

@Getter
@Setter
public class IssueRequest {
    private String title;

    private String description;

    private Issue.Priority priority;

    private Issue.Status status;

    private Long userid;

}
