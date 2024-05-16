package se.issuetrackingsystem.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.project.Project;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> findAllByProject(Project project);
    List<Issue> findAllByProjectAndStatus(Project project, Issue.Status status);
    Issue findByTitle(String Title);
}
