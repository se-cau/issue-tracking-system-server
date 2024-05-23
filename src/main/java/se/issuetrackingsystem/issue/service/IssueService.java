package se.issuetrackingsystem.issue.service;

import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.dto.IssueRequest;
import se.issuetrackingsystem.issue.repository.IssueRepository;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.user.domain.Dev;
import se.issuetrackingsystem.user.domain.ProjectContributor;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.ProjectContributorRepository;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

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

    public void changeStatus(IssueRequest issueRequest, Issue issue){
        if(issue.getStatus()== Issue.Status.NEW){
            issue.setStatus(Issue.Status.ASSIGNED);
            issue.setAssignee(this.userRepository.findById(issueRequest.getUserid()).get());
        }
        else if(issue.getStatus()== Issue.Status.ASSIGNED){
            issue.setStatus(Issue.Status.FIXED);
            issue.setFixer(this.userRepository.findById(issueRequest.getUserid()).get());
        }
        else if(issue.getStatus()== Issue.Status.FIXED){
            issue.setStatus(Issue.Status.RESOLVED);
        }
        else if(issue.getStatus()== Issue.Status.RESOLVED){
            issue.setStatus(Issue.Status.CLOSE);
        }
        else if(issue.getStatus()== Issue.Status.CLOSE){
            issue.setStatus(Issue.Status.REOPENED);
        }
        else if(issue.getStatus()== Issue.Status.REOPENED){
            issue.setStatus(Issue.Status.CLOSE);
        }
        this.issueRepository.save(issue);
    }
    public Optional<User> candidateUser(Issue issue)
    {
        Project project = issue.getProject();
        List<User> users = this.userRepository.findAll();
        ArrayList<User> devs = new ArrayList<>();
        ArrayList<User> projectDevs = new ArrayList<>();
        //모든 developer 추가
        for(User u : users){
            if(u instanceof Dev){
                devs.add(u);
            }
        }
        //프로젝트에 할당된 개발자 식별
        List<ProjectContributor> proconts;
        List<Project> projs;
        for(User u : devs){
            projs = new ArrayList<>();
            proconts = this.projectContributorRepository.findAllByContributor(u);
            for(ProjectContributor pc : proconts){
                projs.add(pc.getProject());
            }
            if(projs.contains(project)){
                projectDevs.add(u);
            }
        }
        //issue title,description 단어 파싱
        String issueTitle = issue.getTitle();
        ArrayList<String> titleWords = new ArrayList<>(Arrays.asList(issueTitle.split(" ")));
        String issueDesc = issue.getDescription();
        ArrayList<String> descWords = new ArrayList<>(Arrays.asList(issueDesc.split(" ")));

        //식별된 dev에 대한 정보객체 생성
        Integer maxP = 0;
        Optional<User> result = Optional.empty();
        for(User d : devs){
            devInfo tempInfo = new devInfo(d,this.issueRepository.findAllByFixer(d));
            for(String s : titleWords){
                if(tempInfo.getIssuesTitleWords().containsKey(s)){
                    tempInfo.addPoints(tempInfo.getIssuesTitleWords().get(s)*10);
                }
            }
            for(String s : descWords){
                if(tempInfo.getIssuesDescriptionWords().containsKey(s)){
                    tempInfo.addPoints(tempInfo.getIssuesDescriptionWords().get(s));
                }
            }
            if(tempInfo.getPoints() > maxP){
                maxP= tempInfo.getPoints();
                result=Optional.ofNullable(tempInfo.getUser());
            }
        }

        return result;
    }

    @Getter
    public class devInfo {
        private User user;
        private ArrayList<Issue> fixedIssues;
        private Map<String, Integer> issuesTitleWords = new HashMap<>();
        private Map<String, Integer> issuesDescriptionWords = new HashMap<>();
        private Integer points;

        devInfo(User dev ,List<Issue> issues) {
            this.points=0;
            this.user=dev;

            fixedIssues = new ArrayList<>(issues);
            ArrayList<String> sTemp;
            for(Issue i : fixedIssues) {
                 sTemp = new ArrayList<>(Arrays.asList(i.getTitle().split(" ")));
                 for(String s : sTemp){
                     if(issuesTitleWords.containsKey(s)){
                         issuesTitleWords.put(s,issuesTitleWords.get(s)+1);
                     }
                     else{
                         issuesTitleWords.put(s,1);
                     }
                 }
            }
            for(Issue i : fixedIssues) {
                sTemp = new ArrayList<>(Arrays.asList(i.getDescription().split(" ")));
                for(String s : sTemp){
                    if(issuesDescriptionWords.containsKey(s)){
                        issuesDescriptionWords.put(s,issuesDescriptionWords.get(s)+1);
                    }
                    else{
                        issuesDescriptionWords.put(s,1);
                    }
                }
            }
        }

        public void addPoints(Integer value){
            this.points+=value;
        }
    }
}
