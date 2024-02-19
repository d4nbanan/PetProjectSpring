package com.example.PetProjectSpring.user.entities;

import com.example.PetProjectSpring.auth.entities.SessionEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<SessionEntity> sessions;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionEntity> sessions) {
        this.sessions = sessions;
    }
}
