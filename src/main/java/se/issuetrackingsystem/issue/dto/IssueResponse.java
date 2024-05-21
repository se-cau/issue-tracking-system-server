package se.issuetrackingsystem.issue.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.issue.domain.Issue;

import java.time.LocalDateTime;

@Getter
@Setter
public class IssueResponse {

    public IssueResponse(Issue issue){
        this.setTitle(issue.getTitle());
        this.setDescription(issue.getDescription());
        if(issue.getReporter()!=null) {
            this.setReporter(issue.getReporter().getUsername());
        }
        if(issue.getAssignee()!=null) {
            this.setReporter(issue.getAssignee().getUsername());
        }
        if(issue.getFixer()!=null) {
            this.setReporter(issue.getFixer().getUsername());
        }
        this.setPriority(issue.getPriority());
        this.setCreated_at(issue.getCreated_at());
        this.setUpdated_at(issue.getUpdated_at());
        this.setStatus(issue.getStatus());
    }
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
