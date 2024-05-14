package se.issuetrackingsystem.Issue;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.UserRepository;
import se.issuetrackingsystem.user.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/issue")
@RequiredArgsConstructor
@Controller
public class IssueController {
    private final IssueService issueService;

    @PostMapping("/{projectid}")
    public void issueCreate(@RequestBody IssueRequest issueRequest, @PathVariable("projectid") Long projectid){
        //Project project = this.projectService.getProject(projectid);
        Project project = new Project(); //temporary
        this.issueService.create(project,issueRequest.getTitle(),issueRequest.getDescription(),issueRequest.getUserid());
    }

    @ResponseBody
    @GetMapping("/{projectid}")
    public List<IssueResponse> issueCheck(@PathVariable("projectid") Long projectid){
        //Project project = this.projectService.getProject(projectid);
        Project project = new Project(); //temporary
        List<Issue> issues = new ArrayList<>();
        issues=this.issueService.getList(project);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return responses;
    }

    @GetMapping("/{issueid}")
    @ResponseBody
    public IssueResponse issueDetail(@PathVariable("issueid") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        IssueResponse issueResponse = new IssueResponse(issue);
        return issueResponse;
    }

    @DeleteMapping("/{issueid}")
    public void issueDelete(@PathVariable("issueid") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.delete(issue);
    }

    @PatchMapping("/{issueid}")
    public void issueModify(@RequestBody IssueRequest issueRequest,@PathVariable("issueid") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.modify(issue,issueRequest.getDescription());
    }

    @PostMapping("/assignees/{issueid}")
    public void issueSetAssignee(@RequestBody IssueRequest issueRequest,@PathVariable("issueid") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.setAssignee(issue,issueRequest.getAssignee_id());
    }

    @GetMapping("/{status}")
    public List<IssueResponse> issueCheckByStatus(@PathVariable("status") Issue.Status status){
        //Project project = this.projectService.getProject(projectid);
        Project project = new Project(); //temporary
        List<Issue> issues = new ArrayList<>();
        issues=this.issueService.getList(project,status);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return responses;
    }

    @PatchMapping("/status/{issueid}")
    public void issueChangeStatus(@RequestBody IssueRequest issueRequest,@PathVariable("issueid") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.changeStatus(issue,issueRequest.getStatus());
    }
}
