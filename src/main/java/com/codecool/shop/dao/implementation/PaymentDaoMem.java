package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.PaymentDao;
import com.codecool.shop.model.Payment;
import com.codecool.shop.model.Shipping;

import java.util.HashMap;
import java.util.Map;

public class PaymentDaoMem implements PaymentDao {

    Map<Integer, Payment> payments = new HashMap<>();
    private static PaymentDaoMem instance = null;

    public static PaymentDaoMem getInstance() {
        if (instance == null) {
            instance = new PaymentDaoMem();
        }
        return instance;
    }

    @Override
    public void addPP(Payment payment) {
        payments.put(payment.getUserId(), payment);
    }

    @Override
    public void addCard(Payment payment) {
        payments.put(payment.getUserId(), payment);
    }

    @Override
    public Payment find(int userId) {
        for (Payment payment : payments.values()) {
            if (payment.getUserId() == userId) {
                return payment;
            }
        }
        return null;
    }
}
