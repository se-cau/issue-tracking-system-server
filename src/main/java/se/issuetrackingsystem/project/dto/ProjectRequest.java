package se.issuetrackingsystem.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "Title은 공백이 될 수 없습니다.")
    private final String title;

    @NotBlank(message = "Admin Id는 공백이 될 수 없습니다.")
    private final Long adminId;

    private final List<Long> contributorIds;

}
