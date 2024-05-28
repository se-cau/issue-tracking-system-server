package se.issuetrackingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.issuetrackingsystem.comment.domain.Comment;
import se.issuetrackingsystem.comment.repository.CommentRepository;
import se.issuetrackingsystem.comment.service.CommentService;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.repository.IssueRepository;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CommentServiceTests {

    CommentService commentService;
    @MockBean
    CommentRepository commentRepository;
    @MockBean
    IssueRepository issueRepository;
    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, issueRepository, userRepository);
    }

    @Test
    @DisplayName("코멘트 생성")
    void commentCreate() {
        //given
        Comment comment = new Comment();
        Issue issue = mock(Issue.class);
        User user = mock(User.class);
        comment.setMessage("Hello");
        comment.setIssue(issue);
        comment.setAuthor(user);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.save(comment)).thenReturn(comment);

        //when
        Comment result = commentService.create(1L, "Hello", 1L);

        //then
        assertEquals("Hello", result.getMessage());
        assertEquals(issue, result.getIssue());
        assertEquals(user, result.getAuthor());
    }

    @Test
    @DisplayName("코멘트 수정")
    void commentModify() {
        //given
        Comment comment = new Comment();
        comment.setMessage("Hello");

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        //when
        Comment result = commentService.modify(comment.getId(), "Hi");

        //then
        assertEquals("Hi", result.getMessage());
    }

    @Test
    @DisplayName("코멘트 리스트 찾기")
    void commentGetList() {
        //given
        Issue issue = mock(Issue.class);

        Comment comment1 = new Comment();
        comment1.setIssue(issue);
        comment1.setId(1L);
        comment1.setMessage("1M");

        Comment comment2 = new Comment();
        comment2.setMessage("2M");
        comment2.setIssue(issue);
        comment2.setId(2L);

        List<Comment> comments = Arrays.asList(
                comment1,comment2
        );

        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));
        when(commentRepository.findAllByIssue(issue)).thenReturn(comments);

        //when
        List<Comment> result = commentService.getList(issue.getId());

        //then
        assertEquals(result.size(),2);
        assertEquals("1M",result.get(0).getMessage());
        assertEquals("2M",result.get(1).getMessage());
    }
}
