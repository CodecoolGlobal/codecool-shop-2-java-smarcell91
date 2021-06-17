package com.codecool.shop.dao.implementation;

import java.util.HashMap;
import java.util.Map;

import com.codecool.shop.dao.BillingDao;
import com.codecool.shop.model.Billing;


public class BillingDaoMem implements BillingDao {
    Map<Integer, Billing> billings = new HashMap<>();
    private static BillingDaoMem instance = null;

    private BillingDaoMem() {
    }

    public static BillingDaoMem getInstance() {
        if (instance == null) {
            instance = new BillingDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Billing billing, int userId) {
        billings.put(userId, billing);
    }

    @Override
    public Billing find(int userId) {
        for (Billing b : billings.values()) {
            if (b.getUserId() == userId) {
                return b;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        Billing billingToRemove = null;
        for (Billing b : billings.values()) {
            if (b.getId() == id) {
                billingToRemove = b;
            }
        }
        billings.remove(billingToRemove.getUserId());
    }

    @Override
    public void update(Billing billing) {
        for (Map.Entry<Integer, Billing> entry : billings.entrySet()) {
            if (entry.getKey() == billing.getUserId()) {
                entry.setValue(billing);
            }
        }
    }
}
