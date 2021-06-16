package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.CartDaoMem;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet(name = "cart", urlPatterns = {"/cart"})
public class Cart extends HttpServlet {
    CartDaoMem cdm = CartDaoMem.getInstance();

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
            context.setVariable("cart", cdm.getCart(userId));
        }
        else
        engine.process("product/cart.html", context, response.getWriter());
    }
}




