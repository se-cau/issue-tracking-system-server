package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Dev")
@NoArgsConstructor
public class Dev extends User {

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
        return false;
    }

    @Override
    public boolean canChangeIssueStateAssignedToFixed() {
        return true;
    }

    @Override
    public boolean canChangeIssueStateFixedToResolved() {
        return false;
    }

    @Override
    public boolean canChangeIssueStateResolvedToClosed() {
        return false;
    }

    public Dev(String username, String password) {
        super(username, password);
    }
}
