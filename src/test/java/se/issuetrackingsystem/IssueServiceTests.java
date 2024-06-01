package se.issuetrackingsystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.repository.IssueRepository;
import se.issuetrackingsystem.issue.service.IssueService;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.projectContributor.domain.ProjectContributor;
import se.issuetrackingsystem.user.domain.*;
import se.issuetrackingsystem.projectContributor.repository.ProjectContributorRepository;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class IssueServiceTests {

    IssueService issueService;
    @MockBean
    IssueRepository issueRepository;
    @MockBean
    ProjectRepository projectRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    ProjectContributorRepository projectContributorRepository;

    @BeforeEach
    void setUp() {
        issueService = new IssueService(issueRepository, userRepository, projectRepository, projectContributorRepository);
    }

    @Test
    @DisplayName("이슈 생성 성공")
    void issueCreate() {
        //given
        Project project = mock(Project.class);
        User user = new Tester();

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(issueRepository.save(any(Issue.class))).thenAnswer(i -> i.getArgument(0));

        //when
        Issue result = issueService.create(project.getId(), "Issue1", "issue", user.getId(), Issue.Priority.MINOR);

        //then
        assertEquals(result.getTitle(), "Issue1");
        assertEquals(result.getDescription(), "issue");
        assertEquals(result.getReporter(), user);
        assertEquals(result.getPriority(), Issue.Priority.MINOR);
        assertEquals(result.getProject(), project);
    }

    @Test
    @DisplayName("이슈 생성 실패-프로젝트_없음")
    void issueCreateNoProject() {
        //given
        Project project = mock(Project.class);
        User user = mock(User.class);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.empty());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(issueRepository.save(any(Issue.class))).thenAnswer(i -> i.getArgument(0));

        //when then
        assertThrows(CustomException.class,
        ()-> issueService.create(project.getId(), "Issue1", "issue", user.getId(), Issue.Priority.MINOR));
    }

    @Test
    @DisplayName("이슈 생성 실패-리포터_없음")
    void issueCreateNoReporter() {
        //given
        Project project = mock(Project.class);
        User user = mock(User.class);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        when(issueRepository.save(any(Issue.class))).thenAnswer(i -> i.getArgument(0));

        //when then
        assertThrows(CustomException.class,
                ()-> issueService.create(project.getId(), "Issue1", "issue", user.getId(), Issue.Priority.MINOR));
    }

    @Test
    @DisplayName("이슈 찾기 성공")
    void getIssue() {
        //given
        Issue issue = new Issue();

        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));

        //when
        Issue result = issueService.getIssue(issue.getId());

        //then
        assertEquals(issue, result);
    }

    @Test
    @DisplayName("프로젝트로 이슈들 찾기")
    void getIssues() {
        //given
        Project project = mock(Project.class);
        Issue issue1 = new Issue();
        issue1.setProject(project);
        Issue issue2 = new Issue();
        issue2.setProject(project);
        List<Issue> issues = Arrays.asList(issue1, issue2);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(issueRepository.findAllByProject(project)).thenReturn(issues);

        //when
        List<Issue> result = this.issueService.getList(project.getId());

        //then
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), issue1);
        assertEquals(result.get(1), issue2);
    }

    @Test
    @DisplayName("Assignee로 이슈들 찾기")
    void getIssuesByAssignee() {
        //given
        User user = mock(User.class);
        Issue issue1 = new Issue();
        issue1.setAssignee(user);
        Issue issue2 = new Issue();
        issue2.setAssignee(user);
        List<Issue> issues = Arrays.asList(issue1, issue2);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(issueRepository.findAllByAssignee(user)).thenReturn(issues);

        //when
        List<Issue> result = this.issueService.getListByAssignee(user.getId());

        //then
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), issue1);
        assertEquals(result.get(1), issue2);
    }

    @Test
    @DisplayName("프로젝트와 상태로 이슈들 찾기")
    void getIssuesWithStatus() {
        //given
        Project project = mock(Project.class);
        Issue issue1 = new Issue();
        issue1.setProject(project);
        issue1.setStatus(Issue.Status.FIXED);
        Issue issue2 = new Issue();
        issue2.setProject(project);
        issue2.setStatus(Issue.Status.FIXED);
        List<Issue> issues = Arrays.asList(issue1, issue2);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(issueRepository.findAllByProjectAndStatus(project, Issue.Status.FIXED)).thenReturn(issues);

        //then
        List<Issue> result = this.issueService.getList(project.getId(), Issue.Status.FIXED);

        //then
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), issue1);
        assertEquals(result.get(1), issue2);
    }

    @Test
    @DisplayName("이슈 수정 성공")
    void issueModify() {
        //given
        Issue issue = new Issue();
        issue.setTitle("Hey");
        issue.setDescription("Apple");
        issue.setPriority(Issue.Priority.MINOR);

        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));
        when(userRepository.findById(any())).thenReturn(Optional.of(new Tester()));

        //when
        Issue result = issueService.modify(issue.getId(), "Hello", "Banana", Issue.Priority.MAJOR,1L);

        //then
        assertEquals(result.getTitle(), "Hello");
        assertEquals(result.getDescription(), "Banana");
        assertEquals(result.getPriority(), Issue.Priority.MAJOR);
    }

    @Test
    @DisplayName("이슈 삭제 성공")
    void issueDelete() {
        //given
        Issue issue = mock(Issue.class);

        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));
        when(userRepository.findById(any())).thenReturn(Optional.of(new Tester()));

        //when
        Issue result = this.issueService.delete(issue.getId(),1L);

        //then
        assertEquals(result, issue);
    }

    @Test
    @DisplayName("이슈 Assignee 설정 성공")
    void setAssignee() {
        //given
        User assignee = new Dev();
        PL pl = new PL();
        Issue issue = new Issue();

        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));
        when(userRepository.findById(any())).thenReturn(Optional.of(pl)).thenReturn(Optional.of(assignee));

        //when
        Issue result = issueService.setAssignee(issue.getId(),pl.getId(),assignee.getId());

        //then
        assertEquals(result.getAssignee(),assignee);
    }

    @Test
    @DisplayName("이슈 Assignee 설정 실패_PL이 아님")
    void setAssigneeFail() {
        //given
        User assignee = mock(User.class);
        User user = mock(User.class);
        Issue issue = new Issue();

        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));
        when(userRepository.findById(any())).thenReturn(Optional.of(user)).thenReturn(Optional.of(assignee));

        //when then
        assertThrows(CustomException.class,
                ()->issueService.setAssignee(issue.getId(),user.getId(),assignee.getId()));
    }

    @Test
    @DisplayName("개발자 추천")
    void candidateDev() {
        //given
        Project project = new Project();

        Dev user1 = new Dev();
        ReflectionTestUtils.setField(user1,"username","User1");
        Dev user2 = new Dev();
        ReflectionTestUtils.setField(user2,"username","User2");
        List<User> users = Arrays.asList(user1,user2);

        ProjectContributor projectContributor1 = new ProjectContributor();
        ReflectionTestUtils.setField(projectContributor1,"project",project);
        ReflectionTestUtils.setField(projectContributor1,"contributor",user1);
        ProjectContributor projectContributor2 = new ProjectContributor();
        ReflectionTestUtils.setField(projectContributor2,"project",project);
        ReflectionTestUtils.setField(projectContributor2,"contributor",user2);
        List<ProjectContributor> projectContributors = Arrays.asList(projectContributor1, projectContributor2);

        Issue issue1 = new Issue();
        issue1.setId(1L);
        issue1.setTitle("A A B");
        issue1.setDescription("B B");
        issue1.setFixer(user1);
        issue1.setStatus(Issue.Status.FIXED);

        Issue issue2 = new Issue();
        issue1.setId(2L);
        issue2.setTitle("B A");
        issue2.setDescription("A A A B B B");
        issue2.setFixer(user2);
        issue2.setStatus(Issue.Status.FIXED);

        Issue testIssue1 = new Issue();
        testIssue1.setId(3L);
        testIssue1.setTitle("A");
        testIssue1.setDescription("A");

        Issue testIssue2 = new Issue();
        testIssue2.setId(4L);
        testIssue2.setTitle("B");
        testIssue2.setDescription("B");

        when(userRepository.findAll()).thenReturn(users);
        when(issueRepository.findById(testIssue1.getId())).thenReturn(Optional.of(testIssue1));
        when(issueRepository.findById(testIssue2.getId())).thenReturn(Optional.of(testIssue2));
        when(projectContributorRepository.findByContributor(user1)).thenReturn(Arrays.asList(projectContributor1));
        when(projectContributorRepository.findByContributor(user2)).thenReturn(Arrays.asList(projectContributor2));
        when(issueRepository.findAllByFixer(user1)).thenReturn(Arrays.asList(issue1));
        when(issueRepository.findAllByFixer(user2)).thenReturn(Arrays.asList(issue2));

        //when
        User result1 = issueService.candidateUser(testIssue1.getId()).get();
        User result2 = issueService.candidateUser(testIssue2.getId()).get();

        //then
        assertEquals(result1.getUsername(),user1.getUsername());
        assertEquals(result2.getUsername(),user2.getUsername());
    }

}