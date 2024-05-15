package se.issuetrackingsystem.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.issuetrackingsystem.exception.CustomException;
import se.issuetrackingsystem.exception.ErrorCode;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.project.dto.ProjectRequest;
import se.issuetrackingsystem.project.repository.ProjectRepository;
import se.issuetrackingsystem.project.service.ProjectService;
import se.issuetrackingsystem.user.domain.Admin;
import se.issuetrackingsystem.user.domain.Contributor;
import se.issuetrackingsystem.user.domain.ProjectContributor;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public void createProject(ProjectRequest request) {

        String title = request.getTitle();
        Long adminId = request.getAdminId();
        List<Long> contributorIds = request.getContributorIds();

        Admin admin = (Admin) userRepository.findById(adminId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Project project = new Project(title, admin);
        addContributors(contributorIds, project);

        projectRepository.save(project);
    }

    private void addContributors(List<Long> contributorIds, Project project) {
        List<ProjectContributor> projectContributors = contributorIds
                .stream()
                .map(id -> {
                    Contributor contributor = (Contributor) userRepository.findById(id)
                            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
                    return new ProjectContributor(project, contributor);
                })
                .toList();

        projectContributors.forEach(project::addContributor);
    }

}
