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
        Supplier nike = new Supplier("Nike", "Clotes");
        supplierDataStore.add(nike);
        Supplier adidas = new Supplier("Adidas", "Clotes");
        supplierDataStore.add(adidas);
        Supplier drink = new Supplier("Drink", "Drink");
        supplierDataStore.add(drink);
        Supplier foods = new Supplier("Food", "Food");
        supplierDataStore.add(foods);
        Supplier other = new Supplier("Other", "Others");
        supplierDataStore.add(other);
        Supplier sony = new Supplier("Sony", "Sony and similar");
        supplierDataStore.add(sony);

        //setting up a new product category
        ProductCategory clotes = new ProductCategory("Clotes", "wear", "Jumpers, T-shirts, Slippers, Shoes, ...");
        productCategoryDataStore.add(clotes);
        ProductCategory game = new ProductCategory("Games", "Hardware", "Electronic games and figures");
        productCategoryDataStore.add(game);
        ProductCategory food = new ProductCategory("Foods", "Foods", "Foods, drinks and candies");
        productCategoryDataStore.add(food);

        //setting up products and printing it
        productDataStore.add(new Product("Mike Shoes", 49.9f, "USD", "Fantastic price. Best Mike shoe.", clotes, nike));
        productDataStore.add(new Product("Hike Slippers", 23, "USD", "Best slippers for summer", clotes, nike));
        productDataStore.add(new Product("Kine Slippers", 38, "USD", "Best new Slippers", clotes, nike));
        productDataStore.add(new Product("Nake jumper", 42, "USD", "Best jumper today", clotes, nike));
        productDataStore.add(new Product("Adcids sport bag", 48, "USD", "Best bag for you", clotes, adidas));
        productDataStore.add(new Product("Adidona soccer shoes", 98, "USD", "Best shoes for soccer", clotes, adidas));
        productDataStore.add(new Product("Johnnie Worker whiskey", 18, "USD", "Original whiskey", food, drink));
        productDataStore.add(new Product("Heimekem beer", 6, "USD", "Best beer", food, drink));
        productDataStore.add(new Product("Olmeo", 3, "USD", "Best biscuit", food, foods));
        productDataStore.add(new Product("Kic Ker", 2, "USD", "Best chocolate", food, foods));
        productDataStore.add(new Product("Poly Station", 76, "USD", "Best console", game, sony));
        productDataStore.add(new Product("FTZA cap", 21, "USD", "Best cap in the world", clotes, other));
        productDataStore.add(new Product("Avangers game", 17, "USD", "Best avangers game", game, other));
        productDataStore.add(new Product("SQMY controller", 35, "USD", "sqmy controller", game, sony));
        productDataStore.add(new Product("Pop Station", 116, "USD", "best portable game", game, sony));
        productDataStore.add(new Product("Nokla phone", 64, "USD", "Best phone in the world", game, other));
        productDataStore.add(new Product("Harry Potter bag", 23, "USD", "Nice bag for kids", clotes, other));
        productDataStore.add(new Product("Game Child", 49, "USD", "Nice game for kids", game, other));
        productDataStore.add(new Product("Special man", 24, "USD", "Nice game for kids", game, other));
    }
}
