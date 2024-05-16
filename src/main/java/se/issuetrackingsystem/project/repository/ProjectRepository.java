package se.issuetrackingsystem.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.issuetrackingsystem.project.domain.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<List<Project>> findByAdminId(Long adminId);

    @Query("SELECT pc.project FROM ProjectContributor pc WHERE pc.contributor.id = :contributorId")
    Optional<List<Project>> findByContributorId(Long contributorId);
}