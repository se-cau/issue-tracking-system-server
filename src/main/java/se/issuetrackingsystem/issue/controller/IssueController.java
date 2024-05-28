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

@Slf4j
@RequestMapping("api/v1/issues")
@RequiredArgsConstructor
@RestController
public class IssueController {
    private final IssueService issueService;

    @PostMapping
    public void issueCreate(@RequestBody IssueRequest issueRequest, @RequestParam("projectId") Long projectId){
        this.issueService.create(projectId,issueRequest.getTitle(),issueRequest.getDescription(),issueRequest.getUserid(),issueRequest.getPriority());
    }

    @GetMapping
    public ResponseEntity<List<IssueResponse>> issueCheck(@RequestParam("projectId") Long projectId){
        List<Issue> issues = this.issueService.getList(projectId);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/details")
    public ResponseEntity<IssueResponse> issueDetail(@RequestParam("issueId") Long issueId){
        Issue issue = this.issueService.getIssue(issueId);
        IssueResponse issueResponse = new IssueResponse(issue);
        return ResponseEntity.ok(issueResponse);
    }

    @DeleteMapping
    public void issueDelete(@RequestParam("issueId") Long issueId){
        this.issueService.delete(issueId);
    }

    @PatchMapping
    public void issueModify(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueId){
        this.issueService.modify(issueId,issueRequest.getTitle(),issueRequest.getDescription(),issueRequest.getPriority());
    }

    @PostMapping("/assignees")
    public void issueSetAssignee(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueId){
        this.issueService.setAssignee(issueId,issueRequest.getUserid(),issueRequest.getAssigneeId());
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<IssueResponse>> issueCheckByStatus(@PathVariable("status") Issue.Status status,@RequestParam("projectId") Long projectId){
        List<Issue> issues = this.issueService.getList(projectId,status);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/status")
    public void issueChangeStatus(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueId){
        this.issueService.changeStatus(issueRequest.getUserid(),issueId);
    }

    @GetMapping("/assigned")
    public ResponseEntity<List<IssueResponse>> issueCheckByAssignee(@RequestParam("userId") Long userId){
        List<Issue> issues = this.issueService.getListByAssignee(userId);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/candidates")
    public ResponseEntity<User> issueCandidate(@RequestParam("issueId") Long issueId) {
        User user = this.issueService.candidateUser(issueId).get();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/statistics/{projectId}")
    public ResponseEntity<IssueStatisticsResponse> getIssueStatistics(@PathVariable Long projectId) {
        return ResponseEntity.ok(issueService.getIssueStatistics(projectId));
    }
}
