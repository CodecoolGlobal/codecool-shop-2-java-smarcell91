package com.codecool.shop.model;

public class Billing {
    private int id;
    private int userId;
    private String country;
    private String city;
    private int zipcode;
    private String address;

    public Billing(String country, String city, int zipcode, String address, int userId) {
        this.country = country;
        this.city = city;
        this.zipcode = zipcode;
        this.address = address;
        this.userId = userId;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }
}
