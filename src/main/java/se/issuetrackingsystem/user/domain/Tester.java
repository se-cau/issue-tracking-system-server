package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Tester")
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
}
