package se.issuetrackingsystem.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProjectRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final Long adminId;

    private final List<Long> contributorIds;

}
