package com.codecool.shop.controller;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    DaoManager daoManager = DaoManager.getInstance();
    UserDao userDao = daoManager.getUserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager daoManager = DaoManager.getInstance();
        UserDao userDao = daoManager.getUserDao();
        HttpSession session = request.getSession();

        boolean isCorrectUser = false;
        boolean missedPassword = false;
        String emailInput = request.getParameter("email");
        String passwordInput = request.getParameter("password");
        User user = userDao.find(emailInput);
        if (user != null) {
            if (hashPassword(passwordInput).equals(user.getPwHash())) {
                isCorrectUser = true;
            } else {
                missedPassword = true;
            }
        } else {
            missedPassword = true;
        }
        session.setAttribute("loggedIn", isCorrectUser);
        session.setAttribute("email", emailInput);
        session.setAttribute("missedPassword", missedPassword);

        response.sendRedirect(request.getContextPath());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected String hashPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return new String(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
