package se.issuetrackingsystem.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.dto.ProjectContributorResponse;
import se.issuetrackingsystem.project.dto.ProjectRequest;
import se.issuetrackingsystem.project.dto.ProjectResponse;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.user.domain.*;
import se.issuetrackingsystem.user.dto.UserResponse;
import se.issuetrackingsystem.user.repository.ProjectContributorRepository;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectContributorRepository projectContributorRepository;

    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {

        String title = request.getTitle();
        Long adminId = request.getAdminId();
        List<Long> contributorIds = request.getContributorIds();

        Project project = new Project(title, getAdmin(adminId));
        projectRepository.save(project);

        addContributors(contributorIds, project);

        return new ProjectResponse(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjects(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Project> projects;

        if (user instanceof Admin) {
            projects = projectRepository.findByAdminId(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
        } else {
            projects = projectRepository.findByContributorId(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
        }

        return projects
                .stream()
                .map(ProjectResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProject(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        return new ProjectResponse(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectContributorResponse> getDevs(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        List<ProjectContributor> projectContributors = projectContributorRepository.findByProject(project)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<ProjectContributor> devContributors = projectContributors.stream()
                .filter(contributor -> Objects.equals(contributor.getContributor().getRole(), "Dev"))
                .toList();

        return devContributors
                .stream()
                .map(ProjectContributorResponse::new)
                .toList();
    }

    protected void addContributors(List<Long> contributorIds, Project project) {

        List<ProjectContributor> projectContributors = contributorIds
                .stream()
                .map(id -> {
                    User contributor = userRepository.findById(id)
                            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

                    if (contributor instanceof Admin) {
                        throw new CustomException(ErrorCode.ROLE_BAD_REQUEST);
                    }
                    
                    ProjectContributorPK projectContributorPK = new ProjectContributorPK(contributor.getId(), project.getId());
                    return new ProjectContributor(projectContributorPK, project, contributor);
                })
                .toList();

        projectContributorRepository.saveAll(projectContributors);

        projectContributors.forEach(project::addContributor);
        projectRepository.save(project);
    }

    private Admin getAdmin(Long adminId) {

        User user = userRepository.findById(adminId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!(user instanceof Admin)) {
            throw new CustomException(ErrorCode.ROLE_BAD_REQUEST);
        }

        Admin admin = (Admin) user;
        return admin;
    }
}
