package com.example.PetProjectSpring.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.PetProjectSpring.user.services.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
}
