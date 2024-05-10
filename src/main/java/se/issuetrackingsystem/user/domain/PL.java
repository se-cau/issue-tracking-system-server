package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PL")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
