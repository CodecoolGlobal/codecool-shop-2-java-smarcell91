package com.codecool.shop.dao.implementation;

import java.util.HashMap;
import java.util.Map;

import com.codecool.shop.dao.ShippingDao;
import com.codecool.shop.model.Shipping;

public class ShippingDaoMem implements ShippingDao {
    Map<Integer, Shipping> shippings = new HashMap<>();
    private static ShippingDaoMem instance = null;

    private ShippingDaoMem() {
    }

    public static ShippingDaoMem getInstance() {
        if (instance == null) {
            instance = new ShippingDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Shipping shipping, int userId) {
        shippings.put(userId, shipping);
    }

    @Override
    public Shipping find(int userId) {
        for (Shipping s : shippings.values()) {
            if (s.getUserId() == userId) {
                return s;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        Shipping shippingToRemove = null;
        for (Shipping s : shippings.values()) {
            if (s.getId() == id) {
                shippingToRemove = s;
            }
        }
        shippings.remove(shippingToRemove.getUserId());
    }

    @Override
    public void update(Shipping shipping) {
        for (Map.Entry<Integer, Shipping> entry : shippings.entrySet()) {
            if (entry.getKey() == shipping.getUserId()) {
                entry.setValue(shipping);
            }
        }
    }
}
