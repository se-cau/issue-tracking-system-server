package se.issuetrackingsystem.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.issuetrackingsystem.comment.domain.Comment;
import se.issuetrackingsystem.comment.repository.CommentRepository;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.repository.IssueRepository;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

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

    public Comment modify(Long commentid,String content){
        Comment comment = this.commentRepository.findById(commentid).orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.setMessage(content);
        this.commentRepository.save(comment);
        return comment;
    }

    public void delete(Long commentid){
        Comment comment = this.commentRepository.findById(commentid).orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        this.commentRepository.delete(comment);
    }

    public List<Comment> getList(Long issueid){
        Issue issue = this.issueRepository.findById(issueid).get();
        List<Comment> comments = this.commentRepository.findAllByIssue(issue);
        return comments;
    }
}
