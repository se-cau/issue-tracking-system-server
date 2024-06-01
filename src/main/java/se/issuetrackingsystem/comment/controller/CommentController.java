package se.issuetrackingsystem.comment.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<CommentResponse>> commentCreate(@RequestParam("issueId") Long issueId, @Valid @RequestBody CommentRequest commentRequest){
        commentService.create(issueId,commentRequest.getMessage(),commentRequest.getUserId());
        List<Comment> comments = commentService.getList(issueId);
        List<CommentResponse> responses = new ArrayList<>();
        for(Comment i : comments){
            responses.add(new CommentResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping
    public ResponseEntity<Comment> commentDelete(@RequestParam("commentId") Long commentId,@Valid @RequestBody CommentRequest commentRequest){
        return ResponseEntity.ok(commentService.delete(commentId,commentRequest.getUserId()));
    }

    @PatchMapping
    public ResponseEntity<Comment> commentModify(@RequestParam("commentId") Long commentId, @Valid @RequestBody CommentRequest commentRequest){
        return ResponseEntity.ok(commentService.modify(commentId, commentRequest.getMessage(),commentRequest.getUserId()));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> commentListGet(@RequestParam("issueId")Long issueId){
        List<Comment> comments = commentService.getList(issueId);
        List<CommentResponse> responses = new ArrayList<>();
        for(Comment i : comments){
            responses.add(new CommentResponse(i));
        }
        return ResponseEntity.ok(responses);
    }
}
