package com.lld.hackathon.repository;

import com.lld.hackathon.entity.User;

import java.util.List;

public interface UserRepo {
    void addOrUpdateUser(User user);

    User findById(String id);

    List<User> getAllUsers();
}
