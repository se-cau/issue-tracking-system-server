package se.issuetrackingsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.issuetrackingsystem.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM users u WHERE TYPE(u) IN (PL, Dev, Tester)")
    Optional<List<User>> findContributors();
}
