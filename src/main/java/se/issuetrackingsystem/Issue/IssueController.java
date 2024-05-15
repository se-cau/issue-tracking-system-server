package se.issuetrackingsystem.Issue;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.Project.Project;
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

    @PostMapping("")
    public void issueCreate(@RequestBody IssueRequest issueRequest, @RequestParam("id") Long projectid){
        //Project project = this.projectService.getProject(projectid);
        Project project = new Project(); //temporary
        this.issueService.create(project,issueRequest.getTitle(),issueRequest.getDescription(),issueRequest.getUserid());
    }

    @ResponseBody
    @GetMapping("")
    public List<IssueResponse> issueCheck(@RequestParam("id") Long projectid){
        //Project project = this.projectService.getProject(projectid);
        Project project = new Project(); //temporary
        List<Issue> issues;
        issues=this.issueService.getList(project);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return responses;
    }

    @GetMapping("")
    @ResponseBody
    public IssueResponse issueDetail(@RequestParam("id") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        IssueResponse issueResponse = new IssueResponse(issue);
        return issueResponse;
    }

    @DeleteMapping("")
    public void issueDelete(@RequestParam("id") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.delete(issue);
    }

    @PatchMapping("")
    public void issueModify(@RequestBody IssueRequest issueRequest,@RequestParam("id") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.modify(issue,issueRequest.getDescription());
    }

    @PostMapping("/assignees")
    public void issueSetAssignee(@RequestBody IssueRequest issueRequest,@RequestParam("id") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.setAssignee(issue,issueRequest.getAssignee_id());
    }

    @GetMapping("")
    public List<IssueResponse> issueCheckByStatus(@RequestParam("stat") Issue.Status status){
        //Project project = this.projectService.getProject(projectid);
        Project project = new Project(); //temporary
        List<Issue> issues;
        issues=this.issueService.getList(project,status);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            responses.add(new IssueResponse(i));
        }
        return responses;
    }

    @PatchMapping("/status")
    public void issueChangeStatus(@RequestBody IssueRequest issueRequest,@RequestParam("id") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.changeStatus(issue,issueRequest.getStatus());
    }
}
