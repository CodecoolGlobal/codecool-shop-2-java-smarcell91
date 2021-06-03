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

@WebServlet(name = "fetchitems", urlPatterns = {"/fetchitems/name", "/fetchitems/price", "/fetchitems/size"})
public class CartProducts extends HttpServlet {
    CartDaoMem cdm = CartDaoMem.getInstance();
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cartJsonString = "";
        if ("name".equals(request.getRequestURI().split("/")[2])) {
            List<String> products = cdm.getProductsNames();
            cartJsonString = this.gson.toJson(products);
        } else if ("price".equals(request.getRequestURI().split("/")[2])) {
            float total = cdm.getPriceSum();
            cartJsonString = this.gson.toJson(total);
        } else if ("size".equals(request.getRequestURI().split("/")[2])) {
            int size = cdm.getCartSize();
            cartJsonString = this.gson.toJson(size);
        }
        PrintWriter out = response.getWriter();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(cartJsonString);
        out.flush();
    }
}