package com.codecool.shop.dao;

import com.codecool.shop.model.Payment;

public interface PaymentDao {
    void addCard(Payment payment);
    void addPP(Payment payment);
    Payment find(int userId);
}
