package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Tester")
@NoArgsConstructor
public class Tester extends User {

    @Override
    public boolean canManageComment() {
        return true;
    }

    @Override
    public boolean canManageIssue() {
        return true;
    }

    @Override
    public boolean canChangeIssueStateFixedToResolved() {
        return true;
    }

    public Tester(String username, String password) {
        super(username, password);
    }
}
