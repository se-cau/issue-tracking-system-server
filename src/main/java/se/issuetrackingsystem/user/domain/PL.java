package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PL")
public class PL extends Contributor {
    @Override
    boolean canManageIssue() {
        return false;
    }

    @Override
    boolean canSetAssignee() {
        return true;
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
        return true;
    }
}
