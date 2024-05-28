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
    public boolean canManageProject() {
        return true;
    }

    @Override
    public boolean canManageComment() {
        return false;
    }

    @Override
    public boolean canManageIssue() {
        return false;
    }

    @Override
    public boolean canSetAssignee() {
        return false;
    }

    @Override
    public boolean canChangeIssueStateAssignedToFixed() {
        return false;
    }

    @Override
    public boolean canChangeIssueStateFixedToResolved() {
        return false;
    }

    @Override
    public boolean canChangeIssueStateResolvedToClosed() {
        return false;
    }

    public Admin(String username, String password) {
        super(username, password);
    }
}
