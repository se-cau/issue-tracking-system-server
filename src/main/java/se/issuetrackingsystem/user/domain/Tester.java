package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Tester")
@NoArgsConstructor
public class Tester extends Contributor {
    @Override
    boolean canManageIssue() {
        return true;
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
        return true;
    }

    @Override
    boolean canChangeIssueStateResolvedToClosed() {
        return false;
    }

    public Tester(String username, String password) {
        super(username, password);
    }
}
