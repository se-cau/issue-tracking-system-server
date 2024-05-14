package se.issuetrackingsystem.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.issuetrackingsystem.user.domain.Admin;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    public Project(String title, Admin admin) {
        this.title = title;
        this.admin = admin;
        this.createdDate = LocalDateTime.now();
    }
}
