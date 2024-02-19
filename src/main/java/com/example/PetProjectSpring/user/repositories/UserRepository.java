package com.example.PetProjectSpring.user.repositories;

import com.example.PetProjectSpring.user.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    
}
