package se.issuetrackingsystem.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.comment.dto.CommentRequest;
import se.issuetrackingsystem.comment.service.CommentService;
import se.issuetrackingsystem.comment.domain.Comment;

@Slf4j
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void commentCreate(@RequestParam("issueId") Long issueid,@RequestBody CommentRequest commentRequest){
        this.commentService.create(issueid,commentRequest.getMessage(),commentRequest.getAuthorid());
    }

    @DeleteMapping
    public void commentDelete(@RequestParam("commentId") Long commentid){
        this.commentService.delete(commentid);
    }

    @PatchMapping
    public void commentModify(@RequestParam("commentId") Long commentid, @RequestBody CommentRequest commentRequest){
        this.commentService.modify(commentid,commentRequest.getMessage());
    }
}
