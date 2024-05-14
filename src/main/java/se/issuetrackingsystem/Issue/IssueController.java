package se.issuetrackingsystem.Issue;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.Project.Project;
import se.issuetrackingsystem.user.User;

import java.security.Principal;
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
        //User user = this.userService.getUser(issueRequest.getUserid());
        User user = new User(); // temporary
        //Project project = this.projectService.getProject(projectid);
        Project project = new Project(); //temporary
        this.issueService.create(project,issueRequest.getTitle(),issueRequest.getDescription(),user);
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
            IssueResponse temp = new IssueResponse();
            temp.setTitle(i.getTitle());
            temp.setDescription(i.getDescription());
            temp.setReporter(i.getReporter().getUsername());
            temp.setAssignee(i.getReporter().getUsername());
            temp.setFixer(i.getFixer().getUsername());
            temp.setNumber(i.getIssue_num());
            temp.setPriority(i.getPriority());
            temp.setCreated_at(i.getCreated_at());
            temp.setUpdated_at(i.getUpdated_at());
            temp.setStatus(i.getStatus());
        }
        return responses;
    }

    @GetMapping("/{issueid}")
    @ResponseBody
    public IssueResponse issueDetail(@PathVariable("issueid") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        IssueResponse issueResponse = new IssueResponse();
        issueResponse.setTitle(issue.getTitle());
        issueResponse.setDescription(issue.getDescription());
        issueResponse.setReporter(issue.getReporter().getUsername());
        issueResponse.setAssignee(issue.getReporter().getUsername());
        issueResponse.setFixer(issue.getFixer().getUsername());
        issueResponse.setNumber(issue.getIssue_num());
        issueResponse.setPriority(issue.getPriority());
        issueResponse.setCreated_at(issue.getCreated_at());
        issueResponse.setUpdated_at(issue.getUpdated_at());
        issueResponse.setStatus(issue.getStatus());
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
        this.issueService.setAssignee(issue,issueRequest.getAssignee());
    }

    @GetMapping("/{status}")
    public List<IssueResponse> issueCheckByStatus(@PathVariable("status") Issue.Status status){
        //Project project = this.projectService.getProject(projectid);
        Project project = new Project(); //temporary
        List<Issue> issues = new ArrayList<>();
        issues=this.issueService.getList(project,status);
        List<IssueResponse> responses = new ArrayList<>();
        for(Issue i : issues){
            IssueResponse temp = new IssueResponse();
            temp.setTitle(i.getTitle());
            temp.setDescription(i.getDescription());
            temp.setReporter(i.getReporter().getUsername());
            temp.setAssignee(i.getReporter().getUsername());
            temp.setFixer(i.getFixer().getUsername());
            temp.setNumber(i.getIssue_num());
            temp.setPriority(i.getPriority());
            temp.setCreated_at(i.getCreated_at());
            temp.setUpdated_at(i.getUpdated_at());
            temp.setStatus(i.getStatus());
        }
        return responses;
    }

    @PatchMapping("/status/{issueid}")
    public void issueChangeStatus(@RequestBody IssueRequest issueRequest,@PathVariable("issueid") Long issueid){
        Issue issue = this.issueService.getIssue(issueid);
        this.issueService.changeStatus(issue,issueRequest.getStatus());
    }
}
