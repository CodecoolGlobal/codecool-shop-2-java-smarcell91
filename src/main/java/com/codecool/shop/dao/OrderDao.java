package com.codecool.shop.dao;

import com.codecool.shop.controller.Cart;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Product;

import java.util.Map;

public interface OrderDao {

    void addCart(CartDaoMem cart);
    void addShipping(Map<String, String> shippingInfo);
    void addPayment(Map<String, String> paymentInfo);
    boolean isJustOrdered();
    void setJustOrdered(boolean justOrdered);
}
