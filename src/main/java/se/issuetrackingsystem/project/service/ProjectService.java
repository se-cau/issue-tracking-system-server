package se.issuetrackingsystem.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.dto.ProjectRequest;
import se.issuetrackingsystem.project.dto.ProjectResponse;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.user.domain.*;
import se.issuetrackingsystem.user.repository.ProjectContributorRepository;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.List;

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

        Admin admin = (Admin) userRepository.findById(adminId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Project project = new Project(title, admin);
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

    protected void addContributors(List<Long> contributorIds, Project project) {
        List<ProjectContributor> projectContributors = contributorIds
                .stream()
                .map(id -> {
                    Contributor contributor = (Contributor) userRepository.findById(id)
                            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
                    ProjectContributorPK projectContributorPK = new ProjectContributorPK(contributor.getId(), project.getId());
                    return new ProjectContributor(projectContributorPK, project, contributor);
                })
                .toList();

        projectContributorRepository.saveAll(projectContributors);

        projectContributors.forEach(project::addContributor);
        projectRepository.save(project);
    }
}
