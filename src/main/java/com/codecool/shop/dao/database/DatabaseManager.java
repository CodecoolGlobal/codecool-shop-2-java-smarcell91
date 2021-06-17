package com.codecool.shop.dao.database;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.CartDaoMem;
import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DatabaseManager {
    private OrderDao orderDao;
    private ProductDao productDao;
    private UserDao userDao;
    private SupplierDao supplierDao;
    private ProductCategoryDao productCategoryDao;
    private CartDao cartDao;
    private BillingDao billingDao;
    private ShippingDao shippingDao;
    private PaymentDao paymentDao;

    public DatabaseManager (String database, String user, String password) throws SQLException {
        DataSource dataSource = connect(database, user, password);
        orderDao = new OrderDaoJdbc(dataSource);
        productDao = new ProductDaoJdbc(dataSource);
        userDao = new UserDaoJdbc(dataSource);
        supplierDao = new SupplierDaoJdbc(dataSource);
        productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
        cartDao = new CartDaoJdbc(dataSource);
        billingDao = new BillingDaoJdbc(dataSource);
        shippingDao = new ShippingDaoJdbc(dataSource);
        paymentDao = new PaymentDaoJdbc(dataSource);
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public SupplierDao getSupplierDao() {
        return supplierDao;
    }

    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public BillingDao getBillingDao() {
        return billingDao;
    }

    public ShippingDao getShippingDao() {
        return shippingDao;
    }

    public PaymentDao getPaymentDao() { return paymentDao; }

    private DataSource connect(String database, String user, String password) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(database);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
