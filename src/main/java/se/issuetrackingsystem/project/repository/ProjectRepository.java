package se.issuetrackingsystem.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.issuetrackingsystem.project.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
