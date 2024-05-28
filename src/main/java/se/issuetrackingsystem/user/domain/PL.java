package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PL")
@NoArgsConstructor
public class PL extends User {

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
        return false;
    }

    @Override
    public boolean canSetAssignee() {
        return true;
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
        return true;
    }

    public PL(String username, String password) {
        super(username, password);
    }
}
