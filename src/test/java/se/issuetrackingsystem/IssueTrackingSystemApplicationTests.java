package se.issuetrackingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.comment.service.CommentService;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.repository.IssueRepository;
import se.issuetrackingsystem.issue.service.IssueService;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.project.service.ProjectService;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.ProjectContributorRepository;
import se.issuetrackingsystem.user.repository.UserRepository;
import se.issuetrackingsystem.user.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class IssueTrackingSystemApplicationTests {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private	UserRepository userRepository;

	/* JUnit Test용 Data
	Admin: 27
	Dev: 28,29
	Project: 27
	Issue: 12,25 (Assigned 29)

	 */

    @Test
    @DisplayName("이슈 생성")
    public void testIssueCreate(){
        Issue issue1 = this.issueService.create(27L,"Hello JUnit","Hello Spring", 28L, Issue.Priority.MAJOR);

        Issue issue2 = this.issueRepository.findById(issue1.getId()).orElseThrow(()->new CustomException(ErrorCode.ISSUE_NOT_FOUND));

        assertEquals(issue1,issue2);
    }

    @Test
    @DisplayName("이슈 찾기")
    public void testIssueGet() {
        Issue issue1 = this.issueService.getIssue(12L);
        this.issueRepository.deleteById(25L);

        Issue issue2 = this.issueRepository.findById(12L).orElseThrow(() -> new CustomException(ErrorCode.ISSUE_NOT_FOUND));

        assertEquals(issue1, issue2);
        assertThrows(CustomException.class,
                () -> this.issueService.getIssue(25L));
    }

    @Test
    @DisplayName("이슈 리스트 찾기")
    public void testIssueGetList() {
        List<Issue> issues1 = this.issueService.getList(27L);

        Project project = this.projectRepository.findById(27L).orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
        List<Issue> issues2 = this.issueRepository.findAllByProject(project);

        assertEquals(issues1, issues2);
    }

    @Test
    @DisplayName("이슈 상태로 찾기")
    public void testIssueGetListWithStatus() {
        List<Issue> issues1 = this.issueService.getList(27L, Issue.Status.NEW);

        Project project = this.projectRepository.findById(27L).orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
        List<Issue> issues2 = this.issueRepository.findAllByProjectAndStatus(project, Issue.Status.NEW);

        assertEquals(issues1, issues2);
    }

    @Test
    @DisplayName("이슈 Assignee로 찾기")
    public void testIssueGetListByAssignee() {
        List<Issue> issues1 = this.issueService.getListByAssignee(29L);

        User user = this.userRepository.findById(29L).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<Issue> issues2 = this.issueRepository.findAllByAssignee(user);

        assertEquals(issues1, issues2);
    }

    @Test
    @DisplayName("이슈 삭제")
    public void testIssueDelete() {
        this.issueService.delete(12L);

        Optional<Issue> issue = this.issueRepository.findById(12L);

        assertTrue(issue.isEmpty());
    }

    @Test
    @DisplayName("Assignee 설정")
    public void testIssueSetAssignee() {
        this.issueService.setAssignee(12L, 30L, 28L);

        User user = this.issueRepository.findById(12L)
                .orElseThrow(() -> new CustomException(ErrorCode.ISSUE_NOT_FOUND))
                .getAssignee();

        assertThrows(CustomException.class,
                () -> this.issueService.setAssignee(12L, 28L, 29L));
        assertEquals(user.getId(), 28L);

    }

    //public void testIssueChangeStatus

    @Test
    @DisplayName("개발자 추천")
    public void testIssueCandidateUser() {
        Issue issue1 = new Issue();
        issue1.setTitle("A A B");
        issue1.setDescription("B B");
        issue1.setFixer(this.userRepository.findById(28L).get());
        issue1.setStatus(Issue.Status.FIXED);
        this.issueRepository.save(issue1);

        Issue issue2 = new Issue();
        issue2.setTitle("B A");
        issue2.setDescription("A A A B B B");
        issue2.setFixer(this.userRepository.findById(29L).get());
        issue2.setStatus(Issue.Status.FIXED);
        this.issueRepository.save(issue2);

        Issue testIssue1 = new Issue();
        testIssue1.setTitle("A");
        testIssue1.setDescription("A");
        this.issueRepository.save(testIssue1);

        Issue testIssue2 = new Issue();
        testIssue2.setTitle("B");
        testIssue2.setDescription("B");
        this.issueRepository.save(testIssue2);

        Optional<User> user1 = this.issueService.candidateUser(testIssue1.getId());
        Optional<User> user2 = this.issueService.candidateUser(testIssue2.getId());

        assertEquals(user1.get().getId(), 28L);
        assertEquals(user2.get().getId(), 29L);
    }
}