package com.codecool.shop.controller;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@WebServlet(name = "registerController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    private DaoManager daoManager = new DaoManager();
    private UserDao userDao = daoManager.getUserDao();
    private String salt = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = hashPassword(request.getParameter("password"));
        User user = new User(firstName, lastName, email, password, salt);
        userDao.add(user);
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
