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
import se.issuetrackingsystem.user.dto.UserResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("api/v1/issues")
@RequiredArgsConstructor
@RestController
public class IssueController {
    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<List<IssueResponse>> issueCreate(@RequestBody IssueRequest issueRequest, @RequestParam("projectId") Long projectId){
        issueService.create(projectId,issueRequest.getTitle(),issueRequest.getDescription(),issueRequest.getUserid(),issueRequest.getPriority());
        List<Issue> issues = issueService.getList(projectId);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<List<IssueResponse>> issueCheck(@RequestParam("projectId") Long projectId){
        List<Issue> issues = issueService.getList(projectId);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/details")
    public ResponseEntity<IssueResponse> issueDetail(@RequestParam("issueId") Long issueId){
        Issue issue = issueService.getIssue(issueId);
        IssueResponse issueResponse = new IssueResponse(issue);
        return ResponseEntity.ok(issueResponse);
    }

    @DeleteMapping
    public ResponseEntity<IssueResponse> issueDelete(@RequestParam("issueId") Long issueId){
        return ResponseEntity.ok(new IssueResponse(issueService.delete(issueId)));
    }

    @PatchMapping
    public ResponseEntity<IssueResponse> issueModify(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueId){
        Issue issue = issueService.modify(issueId,issueRequest.getTitle(),issueRequest.getDescription(),issueRequest.getPriority(),issueRequest.getUserid());
        return ResponseEntity.ok(new IssueResponse(issue));
    }

    @PostMapping("/assignees")
    public ResponseEntity<IssueResponse> issueSetAssignee(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueId){
        Issue issue = issueService.setAssignee(issueId,issueRequest.getUserid(),issueRequest.getAssigneeId());
        return ResponseEntity.ok(new IssueResponse(issue));
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<IssueResponse>> issueCheckByStatus(@PathVariable("status") Issue.Status status,@RequestParam("projectId") Long projectId){
        List<Issue> issues = issueService.getList(projectId,status);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/status")
    public ResponseEntity<IssueResponse> issueChangeStatus(@RequestBody IssueRequest issueRequest,@RequestParam("issueId") Long issueId){
        Issue issue = issueService.changeStatus(issueRequest.getUserid(),issueId);
        return ResponseEntity.ok(new IssueResponse(issue));
    }

    @GetMapping("/assigned")
    public ResponseEntity<List<IssueResponse>> issueCheckByAssignee(@RequestParam("userId") Long userId){
        List<Issue> issues = issueService.getListByAssignee(userId);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/candidates")
    public ResponseEntity<UserResponse> issueCandidate(@RequestParam("issueId") Long issueId) {
        User user = issueService.candidateUser(issueId).get();
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping("/statistics/{projectId}")
    public ResponseEntity<IssueStatisticsResponse> getIssueStatistics(@PathVariable Long projectId) {
        return ResponseEntity.ok(issueService.getIssueStatistics(projectId));
    }
}
