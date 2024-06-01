package se.issuetrackingsystem.projectContributor.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProjectContributorPK implements Serializable {

    private Long contributorId;
    private Long projectId;
}