package com.example.PetProjectSpring.auth.entities;

import com.example.PetProjectSpring.user.entities.UserEntity;
import jakarta.persistence.*;
import jdk.jfr.Relational;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table(name = "sessions")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Date createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivity;

    private String userAgent;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private String refreshToken;
}
