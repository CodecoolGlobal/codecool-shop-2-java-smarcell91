package com.codecool.shop.dao;

import com.codecool.shop.dao.database.DatabaseManager;
import com.codecool.shop.dao.implementation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class DaoManager {
    private OrderDao orderDao;
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private UserDao userDao;
    private CartDao cartDao;
    private static DaoManager instance = null;

    public static DaoManager getInstance() {
        if (instance == null) {
            instance = new DaoManager();
            instance.setup();
        }
        return instance;
    }

    public void setup() {
        String resourceName = "connection.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            props.load(resourceStream);
            if (props.getProperty("dao").equals("jdbc")) {
                DatabaseManager dbManager = new DatabaseManager(props.getProperty("database"), props.getProperty("user"), props.getProperty("password"));
                orderDao = dbManager.getOrderDao();
                productDao = dbManager.getProductDao();
                productCategoryDao = dbManager.getProductCategoryDao();
                supplierDao = dbManager.getSupplierDao();
                userDao = dbManager.getUserDao();
                cartDao = dbManager.getCartDao();
            }
            else {
                orderDao = OrderDaoMem.getInstance();
                productDao = ProductDaoMem.getInstance();
                productCategoryDao = ProductCategoryDaoMem.getInstance();
                supplierDao = SupplierDaoMem.getInstance();
                userDao = UserDaoMem.getInstance();
                cartDao = CartDaoMem.getInstance();
            }
        }
        catch (Exception e) {
            System.out.println("fail");
        }
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    public SupplierDao getSupplierDao() {
        return supplierDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public CartDao getCartDao() {
        return cartDao;
    }
}
