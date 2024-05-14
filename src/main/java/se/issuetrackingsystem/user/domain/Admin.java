package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
import se.issuetrackingsystem.project.domain.Project;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Admin")
@NoArgsConstructor
public class Admin extends User {

    @OneToMany(mappedBy = "admin")
    private List<Project> projects = new ArrayList<>();

    @Override
    boolean canManageProject() {
        return true;
    }

    @Override
    boolean canManageComment() {
        return false;
    }

    @Override
    boolean canManageIssue() {
        return false;
    }

    @Override
    boolean canSetAssignee() {
        return false;
    }

    @Override
    boolean canChangeIssueStateAssignedToFixed() {
        return false;
    }

    @Override
    boolean canChangeIssueStateFixedToResolved() {
        return false;
    }

    @Override
    boolean canChangeIssueStateResolvedToClosed() {
        return false;
    }

    public Admin(String username, String password) {
        super(username, password);
    }
}
