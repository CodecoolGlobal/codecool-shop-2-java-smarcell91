package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface CartDao {
    void add(Product product);
    Product find(int id);
    void remove(int id);
    void decrementAmount(Product product);

    Map<Product, Integer> getCart();
    void setCart(Map<Product, Integer> cart);
    List<String> getProductsNames();
    int getCartSize();
}
