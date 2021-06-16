package com.codecool.shop.dao;

import java.util.List;

import com.codecool.shop.model.Billing;

public interface BillingDao {
    void add(Billing billing, int userId);
    Billing find(int userId);
    void remove(int id);
    List<Billing> getAll();
}
