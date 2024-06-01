package se.issuetrackingsystem.projectContributor.dto;

import lombok.Getter;
import se.issuetrackingsystem.projectContributor.domain.ProjectContributor;

@Getter
public class ProjectContributorResponse {

    private final Long userId;
    private final String username;
    private final String role;

    public ProjectContributorResponse(ProjectContributor projectContributor) {
        this.userId = projectContributor.getContributor().getId();
        this.username = projectContributor.getContributor().getUsername();
        this.role = projectContributor.getContributor().getRole();
    }
}
