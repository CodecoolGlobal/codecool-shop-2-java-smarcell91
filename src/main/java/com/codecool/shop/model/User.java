package com.codecool.shop.model;

public class User {

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
}
