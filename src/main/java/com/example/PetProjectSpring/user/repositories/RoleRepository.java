package com.example.PetProjectSpring.user.repositories;

import com.example.PetProjectSpring.user.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, String> {
    Optional<RoleEntity> findByName(String name);
}
