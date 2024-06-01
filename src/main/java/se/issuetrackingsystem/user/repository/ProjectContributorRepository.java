package se.issuetrackingsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.user.domain.ProjectContributor;
import se.issuetrackingsystem.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectContributorRepository extends JpaRepository<ProjectContributor, Long> {
    List<ProjectContributor> findByContributor(User contributor);

    Optional<List<ProjectContributor>> findByProject(Project project);
}
