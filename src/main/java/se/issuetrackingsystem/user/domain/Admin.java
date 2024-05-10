package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Admin")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User {

    @Override
    boolean canManageProject() {
        return true;
    }

    @Override
    boolean canManageComment() {
        return false;
    }

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
        return false;
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
