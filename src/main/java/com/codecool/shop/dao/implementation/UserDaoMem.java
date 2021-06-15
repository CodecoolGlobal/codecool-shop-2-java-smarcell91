package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMem implements UserDao {

    List<User> users = new ArrayList<>();

    private static UserDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */


    public static UserDaoMem getInstance() {
        if (instance == null) {
            instance = new UserDaoMem();
        }
        return instance;
    }

    public void add(User user) {
        user.setId(users.size()+1);
        users.add(user);
    }

    public User find(int id) {
        for (User user: users) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    public void remove(int id) {
        for (User user: users) {
            if (user.getId() == id) users.remove(user);
        }
    }
    public List<User> allUser() {
        return users;
    }
}
