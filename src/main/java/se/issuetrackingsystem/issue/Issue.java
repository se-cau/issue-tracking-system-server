package se.issuetrackingsystem.issue;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import se.issuetrackingsystem.comment.Comment;
import se.issuetrackingsystem.project.Project;
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

    @OneToOne
    @Nullable
    private User reporter;

    @OneToOne
    @Nullable
    private User assignee;

    @OneToOne
    @Nullable
    private User fixer;

    private Long issue_num;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne
    private User author;

    private LocalDateTime updated_at;

    private LocalDateTime created_at;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE)
    private List<Comment> commentLIst;

    public enum Status{
        NEW,ASSIGNED,FIXED,RESOLVED,CLOSE,REOPENED
    }
    public enum Priority{
        BLOCKER,CRITICAL,MAJOR,MINOR,TRIVIAL
    }
}
