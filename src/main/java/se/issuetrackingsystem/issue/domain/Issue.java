package se.issuetrackingsystem.issue.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.comment.Comment;
import se.issuetrackingsystem.project.domain.Project;
import se.issuetrackingsystem.user.domain.User;

@Getter
@Setter
@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User reporter;

    @ManyToOne
    private User assignee;

    @ManyToOne
    private User fixer;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MAJOR;

    @ManyToOne
    private User author;

    private LocalDateTime updated_at;

    private LocalDateTime created_at;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    public enum Status{
        NEW,ASSIGNED,FIXED,RESOLVED,CLOSE,REOPENED
    }
    public enum Priority{
        BLOCKER,CRITICAL,MAJOR,MINOR,TRIVIAL
    }
}
