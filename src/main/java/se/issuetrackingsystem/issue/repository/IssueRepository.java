package se.issuetrackingsystem.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> findAllByProject(Project project);
    List<Issue> findAllByProjectAndStatus(Project project, Issue.Status status);
    List<Issue> findAllByAssignee(User assignee);
    List<Issue> findAllByFixer(User dev);
}
