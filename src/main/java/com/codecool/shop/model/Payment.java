package com.codecool.shop.model;

public class Payment {
    private int id;
    private int userId;
    private String PPUser;
    private String PPPassword;
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cardCode;

    public Payment(int userId, String PPUser, String PPPassword) {
        this.userId = userId;
        this.PPUser = PPUser;
        this.PPPassword = PPPassword;
    }

    public Payment(int userId, String cardNumber, String cardHolder, String expiryDate, String cardCode) {
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cardCode = cardCode;
    }

    public Payment(int id, int userId, String PPUser, String PPPassword, String cardNumber, String cardHolder, String expiryDate, String cardCode) {
        this.id = id;
        this.userId = userId;
        this.PPUser = PPUser;
        this.PPPassword = PPPassword;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cardCode = cardCode;
    }

    public void setId(int id) {
        this.id = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getPPUser() {
        return PPUser;
    }

    public String getPPPassword() {
        return PPPassword;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCardCode() {
        return cardCode;
    }
}
