package se.issuetrackingsystem.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.issuetrackingsystem.comment.domain.Comment;
import se.issuetrackingsystem.issue.domain.Issue;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{
    List<Comment> findAllByIssue(Issue issue);
}
