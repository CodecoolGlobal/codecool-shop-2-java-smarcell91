package com.codecool.shop.database;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private DataSource dataSource;

    public UserDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    void add(User user) {
        //add user
    }

    User find(int id) {
        //find user
        return User;
    }

    void remove(int id) {
        //remove user
    }

    List<User> allUser() {
        return List<User>;
    }
}
