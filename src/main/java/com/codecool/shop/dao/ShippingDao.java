package com.codecool.shop.dao;

import com.codecool.shop.model.Shipping;

public interface ShippingDao {
    void add(Shipping shipping, int userId);
    Shipping find(int userId);
    void remove(int id);
}
