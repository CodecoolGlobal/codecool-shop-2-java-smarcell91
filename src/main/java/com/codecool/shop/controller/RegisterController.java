package com.codecool.shop.controller;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import com.codecool.shop.service.SendEmail;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.mail.*;

@WebServlet(name = "registerController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    private DaoManager daoManager = DaoManager.getInstance();
    private UserDao userDao = daoManager.getUserDao();
    private CartDao cartDao = daoManager.getCartDao();
    private String salt = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            SendEmail.sendMail(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

//        String password = hashPassword(request.getParameter("password"));
        User user = new User(firstName, lastName, email, password, salt);
        userDao.add(user);
        int userId = userDao.find(email).getId();
        cartDao.addTable(userId);
        response.sendRedirect(request.getContextPath());
    }

    protected String hashPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        this.salt = new String(salt);
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
