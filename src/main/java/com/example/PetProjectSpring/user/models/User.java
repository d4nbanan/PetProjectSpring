package com.example.PetProjectSpring.user.models;

import com.example.PetProjectSpring.user.entities.UserEntity;

public class User {
    private String id;
    private String email;

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setEmail(entity.getEmail());
        model.setId(entity.getId());

        return model;
    }


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
}
