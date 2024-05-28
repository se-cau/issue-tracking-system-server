//package se.issuetrackingsystem;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import se.issuetrackingsystem.comment.domain.Comment;
//import se.issuetrackingsystem.comment.repository.CommentRepository;
//import se.issuetrackingsystem.issue.domain.Issue;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@DataJpaTest
//public class CommentRepositoryTests {
//    @Autowired
//    CommentRepository commentRepository;
//
//    @Test
//    @DisplayName("코멘트 만들기")
//    void createIssue(){
//        //given
//        Comment comment1 = new Comment();
//        comment1.setMessage("hello1");
//
//        Comment comment2 = new Comment();
//        comment2.setMessage("hello2");
//
//        //when
//        Comment result1 = this.commentRepository.save(comment1);
//        Comment result2 = this.commentRepository.save(comment2);
//
//        //then
//        assertEquals(comment1,result1);
//        assertEquals(comment2,result2);
//    }
//
//    @Test
//    @DisplayName("코멘트 찾기")
//    void getIssue(){
//        //given
//        Comment comment1 = new Comment();
//        comment1.setMessage("hello1");
//        this.commentRepository.save(comment1);
//
//        Comment comment2 = new Comment();
//        comment2.setMessage("hello2");
//        this.commentRepository.save(comment2);
//
//        //when
//        Comment result1 = this.commentRepository.findById(comment1.getId()).get();
//        Comment result2 = this.commentRepository.findById(comment2.getId()).get();
//        List<Comment> results = this.commentRepository.findAll();
//
//        //then
//        assertEquals(comment1,result1);
//        assertEquals(comment2,result2);
//        assertEquals(results.size(),2);
//    }
//
//    @Test
//    @DisplayName("이슈 삭제")
//    void deleteIssue() {
//        //given
//        Comment comment1 = new Comment();
//        comment1.setMessage("hello1");
//        this.commentRepository.save(comment1);
//
//        //when
//        this.commentRepository.delete(comment1);
//
//        //then
//        assertTrue(this.commentRepository.findById(comment1.getId()).isEmpty());
//    }
//}
