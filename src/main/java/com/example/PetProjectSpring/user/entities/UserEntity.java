package com.example.PetProjectSpring.user.entities;

import com.example.PetProjectSpring.auth.entities.SessionEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<SessionEntity> sessions;

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<RoleEntity> roles;
}
