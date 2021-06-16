package com.codecool.shop.controller;

import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager daoManager = DaoManager.getInstance();
        UserDao userDao = daoManager.getUserDao();
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        session.setAttribute("userId", userDao.findByEmail(email));
        session.setAttribute("username", email);
        session.setAttribute("password", request.getParameter("password"));
        // TODO: Check in database & log in if correct, error message if not
        response.sendRedirect(request.getContextPath());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
