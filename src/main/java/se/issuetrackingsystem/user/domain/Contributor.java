package se.issuetrackingsystem.user.domain;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@DiscriminatorValue("Contributor")
public abstract class Contributor extends User {
    @Override
    public boolean canManageProject() {
        return false;
    }

    @Override
    public boolean canManageComment() {
        return true;
    }
}
