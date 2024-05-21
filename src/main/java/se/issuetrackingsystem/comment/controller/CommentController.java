package se.issuetrackingsystem.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.comment.dto.CommentRequest;
import se.issuetrackingsystem.comment.service.CommentService;
import se.issuetrackingsystem.comment.domain.Comment;

@Slf4j
@RequestMapping("v1/comment/")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void commentCreate(@RequestParam("issueid") Long issueid,@RequestBody CommentRequest commentRequest){
        this.commentService.create(issueid,commentRequest.getMessage(),commentRequest.getAuthorid());
    }

    @DeleteMapping
    public void commentDelete(@RequestParam("commentid") Long commentid){
        Comment comment=this.commentService.getComment(commentid);
        this.commentService.delete(comment);
    }

    @PatchMapping
    public void commentModify(@RequestParam("commentid") Long commentid, @RequestBody CommentRequest commentRequest){
        Comment comment = this.commentService.getComment(commentid);
        this.commentService.modify(comment,commentRequest.getMessage());
    }
}
