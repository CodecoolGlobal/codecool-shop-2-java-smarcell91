package com.codecool.shop.dao.implementation;

import com.codecool.shop.service.ErrorLogger;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartDaoMem implements CartDao {

    private Map<Integer, List<Product>> cart = new HashMap<Integer, List<Product>>();
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
    public void addTable(int userId) {
        List<Product> userCart = new ArrayList<>();
        cart.put(userId, userCart);
    }

    @Override
    public float getPriceSum(int userId) {
        float maxSum = 0;
        for (Map.Entry<Integer, List<Product>> entry : cart.entrySet()) {
            if (entry.getKey() == userId) {
                for (Product product: entry.getValue()) {
                    maxSum += product.getDefaultPrice();
                }
            }
        }
        return maxSum;
    }

    @Override
    public void add(Product product, int userId) {
        if (cart.containsKey(userId)) {
            for (Map.Entry<Integer, List<Product>> entry : cart.entrySet()) {
                if (entry.getKey() == userId) {
                    entry.getValue().add(product);
                }
            }
        } else {
            List<Product> products = new ArrayList<>();
            products.add(product);
            cart.put(userId, products);
        }
    }

    @Override
    public Product find(int productId, int userId) {
        for (Map.Entry<Integer, List<Product>> entry : cart.entrySet()) {
            if (entry.getKey() == userId) {
                for (Product product: entry.getValue()) {
                    if (product.getId() == productId) {
                        return product;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void decrementAmount(int productId, int userId) {
        for (Map.Entry<Integer, List<Product>> entry : cart.entrySet()) {
            if (entry.getKey() == userId) {
                for (Product product: entry.getValue()) {
                    if (product.getId() == productId) {
                        entry.getValue().remove(product);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void remove(int productId, int userId) {
        for (Map.Entry<Integer, List<Product>> entry : cart.entrySet()) {
            if (entry.getKey() == userId) {
                for (Product product: entry.getValue()) {
                    if (product.getId() == productId) {
                        entry.getValue().remove(product);
                    }
                }
            }
        }
    }

    @Override
    public List<Product> getCart(int userId) {
        for (Map.Entry<Integer, List<Product>> entry : cart.entrySet()) {
            if (entry.getKey() == userId) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void setCart(int userId) {
        for (Map.Entry<Integer, List<Product>> entry : cart.entrySet()) {
            if (entry.getKey() == userId) {
                entry.setValue(new ArrayList<>());
            }
        }
    }
}
