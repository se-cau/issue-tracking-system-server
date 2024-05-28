package se.issuetrackingsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.user.domain.ProjectContributor;
import se.issuetrackingsystem.user.domain.User;

import java.util.List;

@Repository
public interface ProjectContributorRepository extends JpaRepository<ProjectContributor, Long> {
    List<ProjectContributor> findAllByContributor(User contributor);
    List<ProjectContributor> findByProject(Project project);
}
