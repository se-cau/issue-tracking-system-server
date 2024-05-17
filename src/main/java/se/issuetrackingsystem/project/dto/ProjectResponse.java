package se.issuetrackingsystem.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import se.issuetrackingsystem.project.domain.Project;

import java.util.List;

@Getter
public class ProjectResponse {

    @NotBlank
    private final Long projectId;

    @NotBlank
    private final String title;

    @NotBlank
    private final String adminName;

    private final List<String> contributorNames;

    public ProjectResponse(Project project) {
        this.projectId = project.getId();
        this.title = project.getTitle();
        this.adminName = project.getAdmin().getUsername();
        this.contributorNames = project.getProjectContributors()
                .stream()
                .map(projectContributor -> projectContributor.getContributor().getUsername())
                .toList();
    }
}
