package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "fetchitems", urlPatterns = {"/fetchitems/name", "/fetchitems/price", "/fetchitems/size"})
public class CartProducts extends HttpServlet {
    DaoManager daoManager = DaoManager.getInstance();
    CartDao cdm = daoManager.getCartDao();
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        boolean isSessionId = session.getAttribute("userId") != null;
        String cartJsonString = "";

        if (isSessionId) {
            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            if ("name".equals(request.getRequestURI().split("/")[2])) {
                List<String> products = new ArrayList<>();
                for (Product product : cdm.getCart(userId)) {
                    products.add(product.getName());
                }

                cartJsonString = this.gson.toJson(products);
            } else if ("price".equals(request.getRequestURI().split("/")[2])) {
                float total = cdm.getPriceSum(userId);
                cartJsonString = this.gson.toJson(total);
            } else if ("size".equals(request.getRequestURI().split("/")[2])) {
                int size = cdm.getCart(userId).size();
                cartJsonString = this.gson.toJson(size);
            }
        }
        PrintWriter out = response.getWriter();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(cartJsonString);
        out.flush();
    }
}