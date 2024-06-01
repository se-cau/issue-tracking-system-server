package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Dev")
@NoArgsConstructor
public class Dev extends User {

    @Override
    public boolean canManageComment() {
        return true;
    }

    @Override
    public boolean canChangeIssueStateAssignedToFixed() {
        return true;
    }

    public Dev(String username, String password) {
        super(username, password);
    }
}
