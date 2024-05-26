package se.issuetrackingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import se.issuetrackingsystem.user.repository.UserRepository;
import se.issuetrackingsystem.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	PL: 30
	Tester: 31
	Project: 27
	Issue: 12,25 (Assigned 29)

	 */

	@Test
	public void testIssueCreate(){
		Issue issue1 = this.issueService.create(27L,"Hello JUnit","Hello Spring", 28L, Issue.Priority.MAJOR);

		Issue issue2 = this.issueRepository.findById(issue1.getId()).orElseThrow(()->new CustomException(ErrorCode.ISSUE_NOT_FOUND));

		assertEquals(issue1,issue2);
	}

	@Test
	public void testIssueGet(){
		Issue issue1 = this.issueService.getIssue(12L);

		Issue issue2 = this.issueRepository.findById(12L).orElseThrow(()->new CustomException(ErrorCode.ISSUE_NOT_FOUND));

		assertEquals(issue1,issue2);
	}

	@Test
	public void testIssueGetList(){
		List<Issue> issues1 = this.issueService.getList(27L);

		Project project = this.projectRepository.findById(27L).orElseThrow(()->new CustomException(ErrorCode.PROJECT_NOT_FOUND));
		List<Issue> issues2 = this.issueRepository.findAllByProject(project);

		assertEquals(issues1,issues2);
	}

	@Test
	public void testIssueGetListWithStatus(){
		List<Issue> issues1 = this.issueService.getList(27L, Issue.Status.NEW);

		Project project = this.projectRepository.findById(27L).orElseThrow(()->new CustomException(ErrorCode.PROJECT_NOT_FOUND));
		List<Issue> issues2 = this.issueRepository.findAllByProjectAndStatus(project, Issue.Status.NEW);

		assertEquals(issues1,issues2);
	}

	@Test
	public void testIssueGetListByAssignee() {
		List<Issue> issues1 = this.issueService.getListByAssignee(29L);

		User user = this.userRepository.findById(29L).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		List<Issue> issues2 = this.issueRepository.findAllByAssignee(user);

		assertEquals(issues1,issues2);
	}

	@Test
	public void testIssueDelete(){
		this.issueService.delete(12L);
	}


}
