package se.issuetrackingsystem.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
import se.issuetrackingsystem.project.domain.Project;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Admin")
@NoArgsConstructor
public class Admin extends User {

    @OneToMany(mappedBy = "admin")
    private final List<Project> projects = new ArrayList<>();

    @Override
    public boolean canManageProject() {
        return true;
    }

    public Admin(String username, String password) {
        super(username, password);
    }
}
