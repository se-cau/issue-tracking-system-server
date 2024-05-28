//package se.issuetrackingsystem;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import se.issuetrackingsystem.comment.controller.CommentController;
//import se.issuetrackingsystem.comment.domain.Comment;
//import se.issuetrackingsystem.comment.dto.CommentRequest;
//import se.issuetrackingsystem.comment.service.CommentService;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CommentController.class)
//public class CommentControllerTests {
//
//    @Autowired
//    MockMvc mvc;
//
//    @MockBean
//    CommentService commentService;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("코멘트 생성")
//    void testCommentCreate() throws Exception {
//        CommentRequest request = new CommentRequest();
//        request.setMessage("Test Message");
//        request.setAuthorId(1L);
//
//        when(commentService.create(anyLong(),anyString(),anyLong())).thenReturn(new Comment());
//
//        mvc.perform(post("/api/v1/comments")
//                        .param("issueId", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("코멘트 삭제")
//    void testCommentDelete() throws Exception {
//        //given
//        doNothing().when(commentService).delete(anyLong());
//
//        //when then
//        mvc.perform(delete("/api/v1/comments")
//                        .param("commentId", "1"))
//                .andExpect(status().isOk());
//    }
//
//}
