package se.issuetrackingsystem.Issue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.issuetrackingsystem.Project.Project;
import se.issuetrackingsystem.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IssueService {
    private final IssueRepository issueRepository;

    public Issue create(Project project, String title, String description, User reporter){
        Issue issue = new Issue();
        issue.setProject(project);
        issue.setTitle(title);
        issue.setDescription(description);
        issue.setReporter(reporter);
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

    public List<Issue> getList(Project project){
        List<Issue> issues;
        issues = this.issueRepository.findAllByProject(project);
        return issues;
    }

    public List<Issue> getList(Project project, Issue.Status status){
        List<Issue> issues;
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

    public void setAssignee(Issue issue, User user){
        issue.setAssignee(user);
        this.issueRepository.save(issue);
    }

    public void changeStatus(Issue issue, Issue.Status status){
        issue.setStatus(status);
        this.issueRepository.save(issue);
    }
}
