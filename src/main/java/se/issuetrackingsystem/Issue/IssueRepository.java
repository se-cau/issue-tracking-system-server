package se.issuetrackingsystem.Issue;

import org.springframework.data.jpa.repository.JpaRepository;
import se.issuetrackingsystem.Project.Project;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> findAllByProject(Project project);
    List<Issue> findAllByProjectAndStatus(Project project, Issue.Status status);
    Issue findByTitle(String Title);
}
