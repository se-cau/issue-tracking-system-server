package se.issuetrackingsystem.project.service;

import se.issuetrackingsystem.project.dto.ProjectRequest;
import se.issuetrackingsystem.project.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request);

    List<ProjectResponse> getProjects(Long userId);

    ProjectResponse getProject(Long projectId);
}
