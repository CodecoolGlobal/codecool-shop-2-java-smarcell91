package com.codecool.shop.dao.jdbc;
import com.codecool.shop.dao.ProductCategoryDao;

import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCategoryDaoJDBCTest {

    public static final String CONNECTION_TEST_PROPERTIES = "connection_test.properties";
    private static ProductCategoryDao productCategoryDao;
    private static ProductCategory productCategoryOne;
    private static ProductCategory productCategoryTwo;



    @org.junit.jupiter.api.Test
    public void add_addNewProductCategory_doesNotThrowAnException() {
        assertDoesNotThrow(() -> productCategoryDao.add(productCategoryOne));
    }

    @Test
    public void add_addProductCategoryWithNullArgument_throwIllegalArgumentException() {
        ProductCategory productCategoryThree = new ProductCategory(null, "Department", "Description");
        ProductCategory productCategoryFour = new ProductCategory("Name", null, "Description");
        ProductCategory productCategoryFive = new ProductCategory("Name", "Department", null);
        assertThrows(IllegalArgumentException.class, () -> productCategoryDao.add(productCategoryThree));
        assertThrows(IllegalArgumentException.class, () -> productCategoryDao.add(productCategoryFour));
        assertThrows(IllegalArgumentException.class, () -> productCategoryDao.add(productCategoryFive));
    }

    @Test
    public void find_findFirstId_returnTestProductCategoryOne() {
        productCategoryDao.add(productCategoryOne);
        productCategoryDao.add(productCategoryTwo);
        ProductCategory productCategory = productCategoryDao.find(1);
        assertEquals(productCategory.getName(), productCategoryOne.getName());
        assertEquals(productCategory.getDepartment(), productCategoryOne.getDepartment());
        assertEquals(productCategory.getDescription(), productCategoryOne.getDescription());
    }

    @Test
    public void getAll_getAllProductCategory_hasLengthTwo() {
        productCategoryDao.add(productCategoryTwo);
        productCategoryDao.add(productCategoryOne);
        List<ProductCategory> productCategories = productCategoryDao.getAll();
        assertEquals(2, productCategories.size());
    }



}
