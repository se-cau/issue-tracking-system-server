package se.issuetrackingsystem.Comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.Issue.Issue;
import se.issuetrackingsystem.Issue.IssueService;

@Slf4j
@RequestMapping("/comments")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private CommentService commentService;
    private IssueService issueService;

    @PostMapping("")
    public void commentCreate(@RequestParam("id") Long issueid,@RequestBody CommentRequest commentRequest){
        this.commentService.create(issueid,commentRequest.getMessage(),commentRequest.getAuthorid());
    }

    @DeleteMapping("")
    public void commentDelete(@RequestParam("id") Long commentid){
        Comment comment=this.commentService.getComment(commentid);
        this.commentService.delete(comment);
    }

    @PatchMapping("")
    public void commentModify(@RequestParam("id") Long commentid, @RequestBody CommentRequest commentRequest){
        Comment comment = this.commentService.getComment(commentid);
        this.commentService.modify(comment,commentRequest.getMessage());
    }
}
