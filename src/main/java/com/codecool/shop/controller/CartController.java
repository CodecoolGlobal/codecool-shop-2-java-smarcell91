package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import java.io.IOException;

@WebServlet(name = "cartController", urlPatterns = {"/cart/add", "/cart/decrement", "/cart/remove"})
public class CartController extends HttpServlet {
    DaoManager daoManager = DaoManager.getInstance();
    CartDao cartDao = daoManager.getCartDao();
    ProductDao productDao = daoManager.getProductDao();
//    CartDaoMem cdm = CartDaoMem.getInstance();
//    ProductDaoMem pdm = ProductDaoMem.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = Integer.parseInt(session.getAttribute("userId").toString());
        if ("add".equals(request.getRequestURI().split("/")[2])) {
            cartDao.add(productDao.find(Integer.parseInt(request.getParameter("id"))), userId);
        } else if ("remove".equals(request.getRequestURI().split("/")[2])) {
            cartDao.remove(Integer.parseInt(request.getParameter("id")), userId);
            response.sendRedirect("/cart");
        } else if ("decrement".equals(request.getRequestURI().split("/")[2])) {
            cartDao.decrementAmount(Integer.parseInt(request.getParameter("id")), userId);
        }
    }
}
