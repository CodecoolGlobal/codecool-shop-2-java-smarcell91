package com.codecool.shop.model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String pwHash;

    public User(String firstName, String lastName, String email, String pwHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pwHash = pwHash;
    }

    public User(int id, String firstName, String lastName, String email, String pwHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pwHash = pwHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPwHash() {
        return pwHash;
    }

