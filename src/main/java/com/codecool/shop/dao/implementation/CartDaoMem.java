package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import java.util.Map;
import java.util.HashMap;

public class CartDaoMem implements CartDao {
    
    private Map<Product, Integer> cart = new HashMap<Product, Integer>();
    private static CartDaoMem instance = null;

    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        if (cart.containsKey(product)) {
            cart.replace(product, cart.get(product) + 1);
        } else {
            cart.put(product, 1);
        }
    }

    @Override
    public Product find(int id) {
        for (Product p : cart.keySet()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void decrementAmount(Product product) {
        if (cart.containsKey(product)) {
            cart.replace(product, cart.get(product) - 1);
        }
    }

    @Override
    public void remove(int id) {
        Product productToRemove = null;
        for (Product p : cart.keySet()) {
            if (p.getId() == id) {
                productToRemove = p;
            }
        }
        cart.remove(productToRemove);
    }

    @Override
    public Map<Product, Integer> getCart() {
        return cart;
    }
}
