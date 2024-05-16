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
    private String Description;

    private Long assignee_id; //String user name 으로 받아야하나?

    private Issue.Status status;

    private Long userid;

}
