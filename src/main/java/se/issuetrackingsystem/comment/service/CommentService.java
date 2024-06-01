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

    public Comment create(Long issueId, String content, Long authorId){
        Comment comment = new Comment();
        Issue issue = issueRepository.findById(issueId).get();
        comment.setIssue(issue);
        User user = userRepository.findById(authorId).get();
        if(!user.canManageComment()){
            throw new CustomException(ErrorCode.ROLE_FORBIDDEN);
        }
        comment.setAuthor(user);
        comment.setMessage(content);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return comment;
    }

    public Comment modify(Long commentId,String content,Long userid){
        User user = userRepository.findById(userid).get();
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if(user!=comment.getAuthor()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        comment.setMessage(content);
        commentRepository.save(comment);
        return comment;
    }

    public Comment delete(Long commentId,Long userid){
        User user = userRepository.findById(userid).get();
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if(user!=comment.getAuthor()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        commentRepository.delete(comment);
        return comment;
    }

    public List<Comment> getList(Long issueId){
        Issue issue = issueRepository.findById(issueId).orElseThrow(()->new CustomException(ErrorCode.ISSUE_NOT_FOUND));
        List<Comment> comments = commentRepository.findAllByIssue(issue);
        return comments;
    }
}
