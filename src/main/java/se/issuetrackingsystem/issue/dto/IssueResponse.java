package se.issuetrackingsystem.issue.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.issue.domain.Issue;

import java.time.LocalDateTime;

@Getter
@Setter
public class IssueResponse {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    private String reporter;

    private String assignee;

    private String fixer;

    private Issue.Status status;

    private Issue.Priority priority;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public IssueResponse(Issue issue){
        this.setTitle(issue.getTitle());
        this.setId(issue.getId());
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
        this.setCreatedAt(issue.getCreatedAt());
        this.setUpdatedAt(issue.getUpdatedAt());
        this.setStatus(issue.getStatus());
    }
}
