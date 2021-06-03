package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "fetchitems", urlPatterns = {"/fetchitems"})
public class CartProducts extends HttpServlet {
    CartDaoMem cdm = CartDaoMem.getInstance();
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> products = cdm.getProductsNames();
        String cartJsonString = this.gson.toJson(products);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(cartJsonString);
        out.flush();
    }
}