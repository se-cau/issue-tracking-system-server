package se.issuetrackingsystem.user.domain;

import jakarta.persistence.*;
import lombok.*;
import se.issuetrackingsystem.projectContributor.domain.ProjectContributor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "contributor")
    private List<ProjectContributor> projectContributors;

    public boolean canManageProject(){ return false; } // Admin
    public boolean canManageComment(){ return false; } // PL, Dev, Tester
    public boolean canManageIssue(){ return false; } // Tester
    public boolean canSetAssignee(){ return false; } // PL
    public boolean canChangeIssueStateAssignedToFixed(){ return false; } // Dev
    public boolean canChangeIssueStateFixedToResolved(){ return false; } // Tester
    public boolean canChangeIssueStateResolvedToClosed(){ return false; } // PL

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public String getRole() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
