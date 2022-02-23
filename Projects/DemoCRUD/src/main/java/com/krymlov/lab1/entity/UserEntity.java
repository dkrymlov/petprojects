package com.krymlov.lab1.entity;

import com.krymlov.lab1.model.RolesEnum;
import com.krymlov.lab1.model.User;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class UserEntity extends User {

    public UserEntity() {
        super();
    }

    public UserEntity(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password, @NotEmpty @NotNull boolean active, @NotEmpty @NotNull Set<RolesEnum> roles) {
        super(username, password, active, roles);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(nullable = false, length = 60, unique = true)
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Column(nullable = false)
    @Range(min = 6, max = 24)
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Column(nullable = false)
    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }

    @ElementCollection(targetClass = RolesEnum.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Override
    public Set<RolesEnum> getRoles() {
        return super.getRoles();
    }

    @Override
    public void setRoles(Set<RolesEnum> roles) {
        super.setRoles(roles);
    }
}
