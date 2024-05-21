package se.issuetrackingsystem.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.issuetrackingsystem.project.domain.Project;

@Entity
@Getter
@NoArgsConstructor
public class ProjectContributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_contributor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contributor_id")
    private Contributor contributor;

    public ProjectContributor(Project project, Contributor contributor) {
        this.project = project;
        this.contributor = contributor;
    }
}
