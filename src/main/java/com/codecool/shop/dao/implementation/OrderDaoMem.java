package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.Cart;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Product;

import java.util.HashMap;
import java.util.Map;

public class OrderDaoMem implements OrderDao {
    CartDaoMem cart;
    private Map<String, String> shipping = new HashMap<>();
    private Map<String, String> payment = new HashMap<>();
    private static OrderDaoMem instance = null;


    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    public CartDaoMem getCart() {
        return cart;
    }

    public Map<String, String> getShipping() {
        return shipping;
    }

    public Map<String, String> getPayment() { return payment; }

    @Override
    public void addCart(CartDaoMem cart) {
        this.cart = cart;
    }

    @Override
    public void addShipping(Map<String, String> shippingInfo) {
        this.shipping = shippingInfo;
    }

    @Override
    public void addPayment(Map<String, String> paymentInfo) {
        this.payment = paymentInfo;
    }
}
