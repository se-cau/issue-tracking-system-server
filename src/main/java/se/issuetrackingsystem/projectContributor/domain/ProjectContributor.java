package se.issuetrackingsystem.projectContributor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.user.domain.User;

@Entity
@Getter
@NoArgsConstructor
public class ProjectContributor {

    @EmbeddedId
    private ProjectContributorPK projectContributorPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @MapsId("projectId")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contributor_id")
    @MapsId("contributorId")
    private User contributor;

    public ProjectContributor(ProjectContributorPK projectContributorPK, Project project, User contributor) {
        this.projectContributorPK = projectContributorPK;
        this.project = project;
        this.contributor = contributor;
    }
}
