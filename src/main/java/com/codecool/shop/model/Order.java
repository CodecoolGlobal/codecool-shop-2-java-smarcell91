package com.codecool.shop.model;

import java.util.Date;

public class Order {

    private int id;
    private Date date;
    private int userId;
    private String productIds;

    public Order(int id, Date date, int userId, String productIds) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.productIds = productIds;
    }

    public Order(int userId, String productIds) {
        this.userId = userId;
        this.productIds = productIds;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public String getProductIds() {
        return productIds;
    }
}
