package se.issuetrackingsystem.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.comment.dto.CommentRequest;
import se.issuetrackingsystem.comment.dto.CommentResponse;
import se.issuetrackingsystem.comment.service.CommentService;
import se.issuetrackingsystem.comment.domain.Comment;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void commentCreate(@RequestParam("issueId") Long issueId,@RequestBody CommentRequest commentRequest){
        this.commentService.create(issueId,commentRequest.getMessage(),commentRequest.getAuthorId());
    }

    @DeleteMapping
    public void commentDelete(@RequestParam("commentId") Long commentId){
        this.commentService.delete(commentId);
    }

    @PatchMapping
    public void commentModify(@RequestParam("commentId") Long commentId, @RequestBody CommentRequest commentRequest){
        this.commentService.modify(commentId,commentRequest.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> commentListGet(@RequestParam("issueId")Long issueId){
        List<Comment> comments = this.commentService.getList(issueId);
        List<CommentResponse> responses = new ArrayList<>();
        for(Comment i : comments){
            responses.add(new CommentResponse(i));
        }
        return ResponseEntity.ok(responses);
    }
}
