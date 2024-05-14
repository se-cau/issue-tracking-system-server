package se.issuetrackingsystem.Issue;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

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
