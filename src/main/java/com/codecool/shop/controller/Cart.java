package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.model.Product;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "cart", urlPatterns = {"/cart"})
public class Cart extends HttpServlet {
    DaoManager daoManager = DaoManager.getInstance();
    CartDao cartDao = daoManager.getCartDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        if (session.getAttribute("userId") != null) {
            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            context.setVariable("cart", sumOfProducts(cartDao.getCart(userId)));
        }
        engine.process("product/cart.html", context, response.getWriter());
    }

    public Map<Product, Integer> sumOfProducts(List<Product> products) {
        Map<Product, Integer> prodMap = new HashMap<>();
        for (Product product: products) {
            if (prodMap.containsKey(product)) {
                prodMap.replace(product, prodMap.get(product) + 1);
            } else {
                prodMap.put(product, 1);
            }
        }
        return prodMap;
    }
}




