package com.codecool.shop.dao;

import com.codecool.shop.model.Billing;

public interface BillingDao {
    void add(Billing billing, int userId);
    Billing find(int userId);
    void remove(int id);
}
