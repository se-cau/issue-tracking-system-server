package se.issuetrackingsystem.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.dto.IssueStatisticsResponse;
import se.issuetrackingsystem.issue.repository.IssueRepository;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

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

    public IssueStatisticsResponse getIssueStatistics(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
        List<Issue> issues = issueRepository.findAllByProject(project);

        // Issue 상태 분포
        Map<Issue.Status, Long> statusDistribution = issues.stream()
                .collect(Collectors.groupingBy(Issue::getStatus, Collectors.counting()));

        // Reporter 분포
        Map<String, Long> reporterDistribution = issues.stream()
                .collect(Collectors.groupingBy(issue -> issue.getReporter().getUsername(), Collectors.counting()));

        // Assignee 분포
        Map<String, Long> assigneeDistribution = issues.stream()
                .collect(Collectors.groupingBy(issue -> issue.getAssignee().getUsername(), Collectors.counting()));

        // 댓글 개수 상위 이슈 리스트
        List<Issue> topCommentedIssues = issues.stream()
                .sorted(Comparator.comparingInt((Issue issue) -> issue.getCommentList().size()).reversed())
                .limit(5) // 필요한 개수만큼 제한
                .collect(Collectors.toList());

        return new IssueStatisticsResponse(statusDistribution, reporterDistribution, assigneeDistribution, topCommentedIssues);
    }
}
