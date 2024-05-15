package se.issuetrackingsystem.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.issuetrackingsystem.Issue.Issue;
import se.issuetrackingsystem.Issue.IssueRepository;
import se.issuetrackingsystem.Project.Project;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;
    private IssueRepository issueRepository;
    private UserRepository userRepository;

    public Comment create(Long issueid, String content, Long authorid){
        Comment comment = new Comment();
        Issue issue = this.issueRepository.findById(issueid).get();
        comment.setIssue(issue);
        User user = this.userRepository.findById(authorid).get();
        comment.setAuthor(user);
        comment.setMessage(content);
        comment.setCreated_at(LocalDateTime.now());
        this.commentRepository.save(comment);
        return comment;
    }

    public void modify(Comment comment,String content){
        comment.setMessage(content);
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment){
        this.commentRepository.delete(comment);
    }

    public List<Comment> getList(Long issueid){
        List<Comment> comments = this.commentRepository.findAllByIssue(issueid);
        return comments;
    }

    public Comment getComment(Long commentid){
        return this.commentRepository.findById(commentid).get();
    }
}
