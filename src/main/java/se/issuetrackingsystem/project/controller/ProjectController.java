package se.issuetrackingsystem.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.issuetrackingsystem.projectContributor.dto.ProjectContributorResponse;
import se.issuetrackingsystem.project.dto.ProjectRequest;
import se.issuetrackingsystem.project.dto.ProjectResponse;
import se.issuetrackingsystem.project.service.ProjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(@RequestParam Long userId) {
        return ResponseEntity.ok(projectService.getProjects(userId));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProject(projectId));
    }

    @GetMapping("/{projectId}/dev")
    public ResponseEntity<List<ProjectContributorResponse>> getDevs(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getDevs(projectId));
    }

}
