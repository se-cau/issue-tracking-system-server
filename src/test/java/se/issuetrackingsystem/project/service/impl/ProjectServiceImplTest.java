package se.issuetrackingsystem.project.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.project.dto.ProjectRequest;
import se.issuetrackingsystem.project.dto.ProjectResponse;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.user.domain.Admin;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProjectServiceImplTest {

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void createProject() {
        // Given
        Admin admin = new Admin("TestAdmin", "0000");
        userRepository.save(admin);

        ProjectRequest request = new ProjectRequest("Test Project", admin.getId(), Collections.emptyList());

        // When
        ProjectResponse response = projectService.createProject(request);

        // Then
        ProjectResponse project = projectService.getProject(response.getProjectId());
        assertEquals("Test Project", project.getTitle());
        assertEquals("TestAdmin", project.getAdminName());
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
    void getProjects() {
        // Given
        Admin admin = new Admin("TestAdmin", "0000");
        userRepository.save(admin);

        ProjectRequest request = new ProjectRequest("Test Project", admin.getId(), Collections.emptyList());
        projectService.createProject(request);

        // When
        List<ProjectResponse> projects = projectService.getProjects(admin.getId());

        // Then
        assertEquals(1, projects.size());
        assertEquals("Test Project", projects.get(0).getTitle());
        assertEquals("TestAdmin", projects.get(0).getAdminName());
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
        Admin admin = new Admin("TestAdmin", "0000");
        userRepository.save(admin);

        ProjectRequest request = new ProjectRequest("Test Project", admin.getId(), Collections.emptyList());
        ProjectResponse response = projectService.createProject(request);

        // When
        ProjectResponse project = projectService.getProject(response.getProjectId());

        // Then
        assertNotNull(project);
        assertEquals("Test Project", project.getTitle());
        assertEquals("TestAdmin", project.getAdminName());
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