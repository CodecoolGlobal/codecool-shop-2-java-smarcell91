package com.codecool.shop.dao.jdbc;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoJDBCTest {

    public static final String CONNECTION_TEST_PROPERTIES = "connection_test.properties";
    private static ProductCategoryDao productCategoryDao;
    private static SupplierDao supplierDao;
    private static ProductDao productDao;
    private static ProductCategory productCategoryOne;
    private static Supplier supplierOne;
    private static Product productOne;
    private static Product productTwo;

    @BeforeAll
    static void init() {
        productCategoryDao = ProductCategoryDaoMem.getInstance();
        supplierDao = SupplierDaoMem.getInstance();
        productDao = ProductDaoMem.getInstance();
        productCategoryOne  = new ProductCategory("Test P.Category One", "Test P.Category One Department", "Test P.Category One Description");
        supplierOne = new Supplier("Test Supplier One", "Test Supplier One Description");
        productOne  = new Product("Test Product One", 9.9f, "USD", "Test Product One Description", productCategoryOne, supplierOne);
        productTwo = new Product("Test Product Two", 9.8f, "USD", "Test Product Two Description", productCategoryOne, supplierOne);
        productCategoryOne.setId(1);
        supplierOne.setId(1);
    }



    @Test
    public void add_addNewProductWithZeroDefaultPrice_throwAnIllegalArgumentException() {
        Product productZero = new Product("Test Product One", 0.0f, "USD", "Test Product One Description", productCategoryOne, supplierOne);
        assertThrows(IllegalArgumentException.class, () -> productDao.add(productZero));
    }

    @Test
    public void find_productIdOne_returnsProductOne() {
        productCategoryDao.add(productCategoryOne);
        supplierDao.add(supplierOne);
        productDao.add(productOne);
        Product product = productDao.find(1);
        assertEquals(productOne.getName(), product.getName());
    }


}
