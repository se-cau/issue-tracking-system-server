package se.issuetrackingsystem.user.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@NoArgsConstructor
public abstract class Contributor extends User {

    @OneToMany(mappedBy = "contributor")
    private List<ProjectContributor> projectContributors;

    @Override
    public boolean canManageProject() {
        return false;
    }

    @Override
    public boolean canManageComment() {
        return true;
    }

    public Contributor(String username, String password) {
        super(username, password);
    }
}
