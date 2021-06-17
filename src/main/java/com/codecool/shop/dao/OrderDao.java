package com.codecool.shop.dao;

import com.codecool.shop.controller.Cart;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.util.Map;

public interface OrderDao {

    void add(Order order);
    Order find(int userId);
    boolean isJustOrdered();
    void setJustOrdered(boolean justOrdered);
}
