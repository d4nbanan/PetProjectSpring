package com.example.PetProjectSpring.auth.repositories;

import com.example.PetProjectSpring.auth.entities.SessionEntity;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<SessionEntity, String> {

}
