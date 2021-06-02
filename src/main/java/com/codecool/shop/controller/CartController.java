package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import java.io.IOException;

@WebServlet(name = "cartController", urlPatterns = {"/cart/add", "/cart/decrement", "/cart/remove"})
public class CartController extends HttpServlet {
    CartDaoMem cdm = CartDaoMem.getInstance();
    ProductDaoMem pdm = ProductDaoMem.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("add".equals(request.getRequestURI().split("/")[2])) {
            cdm.add(pdm.find(Integer.parseInt(request.getParameter("id"))));
        } else if ("remove".equals(request.getRequestURI().split("/")[2])) {
            cdm.remove(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect("/cart");
        } else if ("decrement".equals(request.getRequestURI().split("/")[2])) {
            cdm.decrementAmount(pdm.find(Integer.parseInt(request.getParameter("id"))));
        }
    }
}
