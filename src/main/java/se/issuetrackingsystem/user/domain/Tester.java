package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Tester")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
