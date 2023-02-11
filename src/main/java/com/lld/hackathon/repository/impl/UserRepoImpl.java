package com.lld.hackathon.repository.impl;

import com.lld.hackathon.entity.User;
import com.lld.hackathon.repository.UserRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    private HashMap<String, User> userHashMap;

    public UserRepoImpl() {
        this.userHashMap = new HashMap<>();
    }

    @Override
    public void addOrUpdateUser(User user) {
        userHashMap.put(user.getId(), user);
    }

    @Override
    public User findById(String id) {
        if(userHashMap.containsKey(id))
            return userHashMap.get(id);

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        for(String key: userHashMap.keySet()) {
            users.add( userHashMap.get(key ));
        }

        return users;
    }
}
