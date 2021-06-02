package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.Map;

public interface CartDao {
    void add(Product product);
    Product find(int id);
    void remove(int id);
    void decrementAmount(Product product);

    Map<Product, Integer> getCart(); 
}
