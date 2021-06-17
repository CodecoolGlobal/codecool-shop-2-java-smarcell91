package com.codecool.shop.dao.jdbc;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



import static org.junit.jupiter.api.Assertions.*;

public class SupplierDaoJDBCTest {
    public static final String CONNECTION_TEST_PROPERTIES = "connection_test.properties";
    private static SupplierDao supplierDao;
    private static Supplier supplierOne;
    private static Supplier supplierTwo;


    @BeforeEach
    void setup() {
        supplierDao = SupplierDaoMem.getInstance();
        supplierOne = new Supplier("Test Supplier One", "Test Supplier One Description");
        supplierTwo = new Supplier("Test Supplier Two", "Test Supplier Two Description");
    }

    @Test
    public void add_addNewSupplier_doesNotThrowAnException() {
        assertDoesNotThrow(() -> supplierDao.add(supplierOne));
    }

    @Test
    public void add_addSupplierWithNullArgument_throwIllegalArgumentException() {
        Supplier supplierThree = new Supplier(null, "Description");
        Supplier supplierFour = new Supplier("Name", null);
        assertThrows(IllegalArgumentException.class, () -> supplierDao.add(supplierThree));
        assertThrows(IllegalArgumentException.class, () -> supplierDao.add(supplierFour));
    }

    @Test
    public void find_findFirstId_returnTestSupplierOne() {
        supplierDao.add(supplierOne);
        Supplier supplier = supplierDao.find(1);
        assertEquals(supplierOne.getName(), supplier.getName());
        assertEquals(supplierOne.getDescription(), supplier.getDescription());
    }


    @Test
    public void find_findSecondId_returnTestSupplierTwo() {
        supplierDao.add(supplierOne);
        supplierDao.add(supplierTwo);
        Supplier supplier = supplierDao.find(2);
        assertEquals(supplierTwo.getName(), supplier.getName());
        assertEquals(supplierTwo.getDescription(), supplier.getDescription());
    }


}
