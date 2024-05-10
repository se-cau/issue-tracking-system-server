package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Dev")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dev extends Contributor {
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
        return true;
    }

    @Override
    boolean canChangeIssueStateFixedToResolved() {
        return false;
    }

    @Override
    boolean canChangeIssueStateResolvedToClosed() {
        return false;
    }
}
