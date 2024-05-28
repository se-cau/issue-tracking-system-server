package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Tester")
@NoArgsConstructor
public class Tester extends User {

    @Override
    public boolean canManageProject() {
        return false;
    }

    @Override
    public boolean canManageComment() {
        return true;
    }

    @Override
    public boolean canManageIssue() {
        return true;
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
        return true;
    }

    @Override
    public boolean canChangeIssueStateResolvedToClosed() {
        return false;
    }

    public Tester(String username, String password) {
        super(username, password);
    }
}
