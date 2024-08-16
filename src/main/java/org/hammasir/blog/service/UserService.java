package org.hammasir.blog.service;

import org.hammasir.blog.entity.User;
import org.hammasir.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        users.addAll(userRepository.findAll());

        return users;
    }
}