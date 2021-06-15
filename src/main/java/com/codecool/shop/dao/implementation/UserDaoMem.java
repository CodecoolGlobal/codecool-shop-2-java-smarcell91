package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.User;

import java.util.ArrayList;

public class UserDaoMem {

    private static UserDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */


    public static UserDaoMem getInstance() {
        if (instance == null) {
            instance = new UserDaoMem();
        }
        return instance;
    }
}
