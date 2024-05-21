package se.issuetrackingsystem.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.repository.IssueRepository;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.user.domain.Dev;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.ProjectContributorRepository;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectContributorRepository projectContributorRepository;

    public Issue create(Long projectid, String title, String description, Long reporterid){
        Issue issue = new Issue();
        Project project = this.projectRepository.findById(projectid).get();
        issue.setProject(project);
        issue.setTitle(title);
        issue.setDescription(description);
        issue.setReporter(userRepository.findById(reporterid).get());
        issue.setCreated_at(LocalDateTime.now());
        this.issueRepository.save(issue);
        return issue;
    }

    public Issue getIssue(Long id){
        Optional<Issue> issue=this.issueRepository.findById(id);
        if(issue.isPresent()){
            return issue.get();
        }
        else{
            throw new RuntimeException();
        }
    }

    public List<Issue> getList(Long projectid){
        List<Issue> issues;
        Project project = this.projectRepository.findById(projectid).get();
        issues = this.issueRepository.findAllByProject(project);
        return issues;
    }

    public List<Issue> getListByAssignee(Long userid){
        List<Issue> issues;
        User user = this.userRepository.findById(userid).get();
        issues = this.issueRepository.findAllByAssignee(user);
        return issues;
    }

    public List<Issue> getList(Long projectid, Issue.Status status){
        List<Issue> issues;
        Project project = this.projectRepository.findById(projectid).get();
        issues = this.issueRepository.findAllByProjectAndStatus(project,status);
        return issues;
    }

    public void modify(Issue issue,String description){
        issue.setDescription(description);
        issue.setUpdated_at(LocalDateTime.now());
        this.issueRepository.save(issue);
    }

    public void delete(Issue issue){
        this.issueRepository.delete(issue);
    }

    public void setAssignee(Issue issue, Long assigneeid){
        issue.setAssignee(userRepository.findById(assigneeid).get());
        this.issueRepository.save(issue);
    }

    public void changeStatus(Issue issue, Issue.Status status){
        issue.setStatus(status);
        this.issueRepository.save(issue);
    }
    /*
    public User candidateUser(Issue issue)
    {
        Project project = issue.getProject();
        List<User> users = this.userRepository.findAll();
        ArrayList<User> devs = new ArrayList<>();
        for(User u : users){
            if(u instanceof Dev){
                devs.add(u);
            }
        }
        List<Project> projs;
        for(User u :devs){
            projs = this.projectContributorRepository.findAllByContributor(u);
            if(!projs.contains(project)){
                devs.remove(u);
            }
        }
    }
    */
}
