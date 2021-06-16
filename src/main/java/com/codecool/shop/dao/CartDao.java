package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface CartDao {
    void add(Product product, int userId);
    Product find(int productId, int userId);
    void remove(int productId, int userId);
    void decrementAmount(int productId, int userId);
    float getPriceSum(int userId);
    List<Product> getCart(int userId);
    void setCart(Map<Integer, List<Product>> cart);
}
