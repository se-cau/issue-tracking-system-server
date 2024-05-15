package se.issuetrackingsystem.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.issuetrackingsystem.project.dto.ProjectRequest;
import se.issuetrackingsystem.project.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public void createProject(@RequestBody ProjectRequest request) {
        projectService.createProject(request);
    }

}
