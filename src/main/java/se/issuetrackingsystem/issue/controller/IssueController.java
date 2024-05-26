package se.issuetrackingsystem.issue.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.issue.dto.IssueStatisticsResponse;
import se.issuetrackingsystem.issue.service.IssueService;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.dto.IssueRequest;
import se.issuetrackingsystem.issue.dto.IssueResponse;
import se.issuetrackingsystem.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("api/v1/issues")
@RequiredArgsConstructor
@RestController
public class IssueController {
    private final IssueService issueService;

    @PostMapping
    public void issueCreate(@RequestBody IssueRequest issueRequest, @RequestParam("projectId") Long projectid){
        this.issueService.create(projectid,issueRequest.getTitle(),issueRequest.getDescription(),issueRequest.getUserid(),issueRequest.getPriority());
    }

    @GetMapping
    public ResponseEntity<List<IssueResponse>> issueCheck(@RequestParam("projectId") Long projectid){
        List<Issue> issues = this.issueService.getList(projectid);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/details")
    public ResponseEntity<IssueResponse> issueDetail(@RequestParam("issueId") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        IssueResponse issueResponse = new IssueResponse(issue);
        return ResponseEntity.ok(issueResponse);
    }

    @DeleteMapping
    public void issueDelete(@RequestParam("issueId") Long issueid){
        this.issueService.delete(issueid);
    }

    @PatchMapping
    public void issueModify(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueid){
        this.issueService.modify(issueid,issueRequest.getTitle(),issueRequest.getDescription(),issueRequest.getPriority());
    }

    @PostMapping("/assignees")
    public void issueSetAssignee(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueid){
        this.issueService.setAssignee(issueid,issueRequest.getUserid(),issueRequest.getAssigneeid());
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<IssueResponse>> issueCheckByStatus(@PathVariable("status") Issue.Status status,@RequestParam("projectId") Long projectid){
        List<Issue> issues = this.issueService.getList(projectid,status);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/status")
    public void issueChangeStatus(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueid){
        this.issueService.changeStatus(issueRequest.getUserid(),issueid);
    }

    @GetMapping("/assigned")
    public ResponseEntity<List<IssueResponse>> issueCheckByAssignee(@RequestParam("userId") Long userid){
        List<Issue> issues = this.issueService.getListByAssignee(userid);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/candidates")
    public ResponseEntity<User> issueCandidate(@RequestParam("issueId") Long issueid) {
        User user = this.issueService.candidateUser(issueid).get();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/statistics/{projectId}")
    public ResponseEntity<IssueStatisticsResponse> getIssueStatistics(@PathVariable Long projectId) {
        return ResponseEntity.ok(issueService.getIssueStatistics(projectId));
    }
}
