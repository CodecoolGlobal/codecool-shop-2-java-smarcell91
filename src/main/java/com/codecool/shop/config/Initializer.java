package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);


        Supplier hp = new Supplier("HP", "Computers");
        supplierDataStore.add(hp);
        Supplier samsung = new Supplier("samsung", "mobil");
        supplierDataStore.add(samsung);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);

        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "Portable computer");
        productCategoryDataStore.add(laptop);
        ProductCategory mobil = new ProductCategory("Mobil", "Mobil phone", "Mobil phones and tablets");
        productCategoryDataStore.add(mobil);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));

        productDataStore.add(new Product("HP laptop", 220, "USD", "HP's best laptop.", laptop, hp));
        productDataStore.add(new Product("HP mobil", 220, "USD", "HP's best phone.", mobil, hp));
        productDataStore.add(new Product("Samsung xyz mobil", 130, "USD", "Samsung's best phone.", mobil, samsung));
        productDataStore.add(new Product("Samsung laptop", 250, "USD", "Samsung's best laptop.", laptop, samsung));
        productDataStore.add(new Product("Samsung tablet", 250, "USD", "Samsung's best tablet.", tablet, samsung));
        productDataStore.add(new Product("Amazon superPhone", 250, "USD", "Amazon's best phone.", mobil, amazon));
    }
}
