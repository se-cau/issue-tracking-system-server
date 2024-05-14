package se.issuetrackingsystem.Issue;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.user.User;

@Getter
@Setter
public class IssueRequest {
    @NotEmpty
    private String title;

    @NotEmpty
    private String Description;

    private User assignee; //String user name 으로 받아야하나?

    private Issue.Status status;

    private String userid;

}
