package se.issuetrackingsystem.issue.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.issue.domain.Issue;

@Getter
@Setter
public class IssueRequest {
    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    private Long assigneeid;

    private Issue.Status status;

    private Long userid;

}
