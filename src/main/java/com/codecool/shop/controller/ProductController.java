package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore,productCategoryDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String selectedCategory = req.getParameter("selectedCategory");
        String selectedSupplier = req.getParameter("selectedSupplier");

        if (selectedCategory == null && selectedSupplier == null){
            Map<String, Integer> category = new HashMap<>();
            category.put("id", productCategoryDataStore.getAll().size()+1);
            Map<String, Integer> supplier = new HashMap<>();
            supplier.put("id", supplierDataStore.getAll().size()+1);
            context.setVariable("category", category);
            context.setVariable("supplier", supplier);
            context.setVariable("products", productDataStore.getAll());
        }
        else if (selectedCategory == null && Integer.parseInt(selectedSupplier) == supplierDataStore.getAll().size()+1){
            Map<String, Integer> category = new HashMap<>();
            category.put("id", productCategoryDataStore.getAll().size()+1);
            Map<String, Integer> supplier = new HashMap<>();
            supplier.put("id", supplierDataStore.getAll().size()+1);
            context.setVariable("category", category);
            context.setVariable("supplier", supplier);
            context.setVariable("products", productDataStore.getAll());
        }
        else if (selectedSupplier == null && Integer.parseInt(selectedCategory) == productCategoryDataStore.getAll().size()+1){
            Map<String, Integer> category = new HashMap<>();
            category.put("id", productCategoryDataStore.getAll().size()+1);
            Map<String, Integer> supplier = new HashMap<>();
            supplier.put("id", supplierDataStore.getAll().size()+1);
            context.setVariable("category", category);
            context.setVariable("supplier", supplier);
            context.setVariable("products", productDataStore.getAll());
        }
        else if (selectedSupplier == null && Integer.parseInt(selectedCategory) != productCategoryDataStore.getAll().size()+1) {
            int categoryId = Integer.parseInt(selectedCategory);
            Map<String, Integer> supplier = new HashMap<>();
            supplier.put("id", supplierDataStore.getAll().size()+1);
            context.setVariable("category", productService.getProductCategory(categoryId));
            context.setVariable("products", productService.getProductsForCategory(categoryId));
            context.setVariable("supplier", supplier);
        }
        else if (selectedCategory == null && Integer.parseInt(selectedSupplier) != supplierDataStore.getAll().size()+1) {
            Map<String, Integer> category = new HashMap<>();
            category.put("id", productCategoryDataStore.getAll().size()+1);
            int supplierId = Integer.parseInt(selectedSupplier);
            Supplier supplier = supplierDataStore.find(supplierId);
            context.setVariable("products", supplier.getProducts());
            context.setVariable("supplier", supplier);
            context.setVariable("category", category);

        }
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        var cart = cartDaoMem.getCart();
        context.setVariable("cartsize", cart.size());
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

}
