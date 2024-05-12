package se.issuetrackingsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.issuetrackingsystem.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
