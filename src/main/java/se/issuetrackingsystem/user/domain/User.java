package se.issuetrackingsystem.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    abstract boolean canManageProject(); // Admin
    abstract boolean canManageComment(); // PL, Dev, Tester
    abstract boolean canManageIssue(); // Tester
    abstract boolean canSetAssignee(); // PL
    abstract boolean canChangeIssueStateAssignedToFixed(); // Dev
    abstract boolean canChangeIssueStateFixedToResolved(); // Tester
    abstract boolean canChangeIssueStateResolvedToClosed(); // PL
}
