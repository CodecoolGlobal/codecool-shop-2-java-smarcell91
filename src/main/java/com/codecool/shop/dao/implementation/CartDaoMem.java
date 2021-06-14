package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartDaoMem implements CartDao {
    private static Logger logger = LoggerFactory.getLogger(CartDaoMem.class);
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

    public float getPriceSum() {
        float maxSum = 0;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            maxSum += entry.getKey().getDefaultPrice() * entry.getValue();
        }
        return maxSum;
    }

    @Override
    public void add(Product product) {
        logger.info("This is an information message");
        logger.error("this is a error message");
        logger.warn("this is a warning message");
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

    @Override
    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    @Override
    public List<String> getProductsNames() {
        var products = new ArrayList<String>();
        for (Product product : cart.keySet()) {
            products.add(product.getName());
        }
        return products;
    }

    @Override
    public int getCartSize() {
        int size = 0;
        for (int value : cart.values()) {
            size += Integer.valueOf(value);
        }
        return size;
    }
}
