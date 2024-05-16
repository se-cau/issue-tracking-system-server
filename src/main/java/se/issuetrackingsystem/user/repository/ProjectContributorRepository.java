package se.issuetrackingsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.issuetrackingsystem.user.domain.ProjectContributor;

@Repository
public interface ProjectContributorRepository extends JpaRepository<ProjectContributor, Long> {
}
