package com.codecool.shop.database;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CartDaoMem;

import javax.sql.DataSource;
import java.util.Map;

public class OrderDaoJdbc implements OrderDao {

    private DataSource dataSource;

    public OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    void addCart(CartDaoMem cart);
    void addShipping(Map<String, String> shippingInfo);
    void addPayment(Map<String, String> paymentInfo);
}
