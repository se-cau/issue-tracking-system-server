package se.issuetrackingsystem.project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.dto.ProjectRequest;
import se.issuetrackingsystem.project.dto.ProjectResponse;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.projectContributor.domain.ProjectContributor;
import se.issuetrackingsystem.projectContributor.domain.ProjectContributorPK;
import se.issuetrackingsystem.projectContributor.dto.ProjectContributorResponse;
import se.issuetrackingsystem.user.domain.*;
import se.issuetrackingsystem.projectContributor.repository.ProjectContributorRepository;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectContributorRepository projectContributorRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void createProject() {
        // Given
        Admin admin = new Admin("TestAdmin", passwordEncoder.encode("0000"));
        userRepository.save(admin);

        ProjectRequest request = new ProjectRequest("Test Project", admin.getId(), Collections.emptyList());

        // When
        ProjectResponse response = projectService.createProject(request);

        // Then
        assertEquals("Test Project", response.getTitle());
        assertEquals("TestAdmin", response.getAdminName());
    }

    @Test
    void createProjectWithInvalidAdmin() {
        // Given
        Long invalidAdminId = 999L;
        ProjectRequest request = new ProjectRequest("Test Project", invalidAdminId, Collections.emptyList());

        // When, Then
        CustomException exception = assertThrows(CustomException.class, () -> projectService.createProject(request));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void addContributors() {

        Admin admin = new Admin("TestAdmin", passwordEncoder.encode("0000"));
        userRepository.save(admin);

        Project project = new Project("Test Project", admin);
        projectRepository.save(project);

        Dev user1 = new Dev("TestDev", passwordEncoder.encode("0000"));
        Tester user2 = new Tester("TestTester", passwordEncoder.encode("0000"));
        userRepository.save(user1);
        userRepository.save(user2);

        List<Long> contributorIds = Arrays.asList(user1.getId(), user2.getId());
        projectService.addContributors(contributorIds, project);

        List<ProjectContributor> projectContributors = projectContributorRepository.findByProject(project)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        assertNotNull(projectContributors);
        assertEquals(2, projectContributors.size());
    }

    @Test
    void getProjects() {
        // Given
        Admin admin = new Admin("TestAdmin", passwordEncoder.encode("0000"));
        userRepository.save(admin);

        Project project = new Project("Test Project", admin);
        projectRepository.save(project);

        // When
        List<ProjectResponse> response = projectService.getProjects(admin.getId());

        // Then
        assertEquals(1, response.size());
        assertEquals("Test Project", response.get(0).getTitle());
        assertEquals("TestAdmin", response.get(0).getAdminName());
    }

    @Test
    void getProjectsForInvalidUser() {
        // Given
        Long invalidUserId = 999L;

        // When, Then
        CustomException exception = assertThrows(CustomException.class, () -> projectService.getProjects(invalidUserId));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void getProject() {
        // Given
        Admin admin = new Admin("TestAdmin", passwordEncoder.encode("0000"));
        userRepository.save(admin);

        Project project = new Project("Test Project", admin);
        projectRepository.save(project);

        // When
        ProjectResponse response = projectService.getProject(project.getId());

        // Then
        assertNotNull(project);
        assertEquals("Test Project", response.getTitle());
        assertEquals("TestAdmin", response.getAdminName());
    }

    @Test
    void getDevs() {

        // Given
        Admin admin = new Admin("TestAdmin", passwordEncoder.encode("0000"));
        userRepository.save(admin);

        Project project = new Project("Test Project", admin);
        projectRepository.save(project);

        User dev1 = new Dev("Dev1", passwordEncoder.encode("0000"));
        User dev2 = new Dev("Dev2", passwordEncoder.encode("0000"));
        User tester = new Tester("Tester", passwordEncoder.encode("0000"));
        userRepository.save(dev1);
        userRepository.save(dev2);
        userRepository.save(tester);

        ProjectContributorPK projectContributorPK1 = new ProjectContributorPK(dev1.getId(), project.getId());
        ProjectContributorPK projectContributorPK2 = new ProjectContributorPK(dev2.getId(), project.getId());
        ProjectContributorPK projectContributorPK3 = new ProjectContributorPK(tester.getId(), project.getId());

        ProjectContributor contributor1 = new ProjectContributor(projectContributorPK1, project, dev1);
        ProjectContributor contributor2 = new ProjectContributor(projectContributorPK2, project, dev2);
        ProjectContributor contributor3 = new ProjectContributor(projectContributorPK3, project, tester);
        projectContributorRepository.save(contributor1);
        projectContributorRepository.save(contributor2);
        projectContributorRepository.save(contributor3);

        // When
        List<ProjectContributorResponse> devs = projectService.getDevs(project.getId());

        // Then
        assertNotNull(devs);
        assertEquals(2, devs.size());
        assertTrue(devs.stream().anyMatch(dev -> dev.getUsername().equals("Dev1")));
        assertTrue(devs.stream().anyMatch(dev -> dev.getUsername().equals("Dev2")));
        assertFalse(devs.stream().anyMatch(dev -> dev.getUsername().equals("Tester")));
    }

    @Test
    void getNonExistingProject() {
        // Given
        Long nonExistingProjectId = 999L;

        // When, Then
        CustomException exception = assertThrows(CustomException.class, () -> projectService.getProject(nonExistingProjectId));
        assertEquals(ErrorCode.PROJECT_NOT_FOUND, exception.getErrorCode());
    }
}