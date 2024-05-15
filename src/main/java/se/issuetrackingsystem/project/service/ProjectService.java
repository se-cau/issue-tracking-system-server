package se.issuetrackingsystem.project.service;

import se.issuetrackingsystem.project.dto.ProjectRequest;

public interface ProjectService {

    void createProject(ProjectRequest request);

//    ProjectResponse getProject(Long projectId);
//
//    List<ProjectResponse> getProjects(Long userId);
}
