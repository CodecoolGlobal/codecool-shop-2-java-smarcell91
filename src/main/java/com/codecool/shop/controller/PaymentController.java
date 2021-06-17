package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Payment;
import com.codecool.shop.model.Product;

import com.codecool.shop.service.Json;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = {"/payment"} )
public class PaymentController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoManager daoManager = DaoManager.getInstance();
        CartDao cartDataStore = daoManager.getCartDao();
        OrderDao orderDataStore = daoManager.getOrderDao();
        UserDao userDao = daoManager.getUserDao();
        ShippingDao shippingDao = daoManager.getShippingDao();
        PaymentDao paymentDao = daoManager.getPaymentDao();
        ProductDao productDao = daoManager.getProductDao();

        String payPalUserName = req.getParameter("payPalUsername");
        String payPalPW = req.getParameter("payPalPW");
        String cardNumber = req.getParameter("cardNumber");
        String cardHolder = req.getParameter("cardHolder");
        String expiryDate = req.getParameter("expiryDate");
        String cardCode = req.getParameter("cardCode");
        String payPalCheckbox = req.getParameter("payPalCheckbox");
        String cardCheckbox = req.getParameter("cardCheckbox");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = req.getSession();
        int userId = Integer.parseInt(session.getAttribute("userId").toString());

        if (Objects.equals(payPalCheckbox, "on") && cardCheckbox == null &&
                payPalUserName != null && payPalPW != null) {
            Payment payment = new Payment(userId, payPalUserName, payPalPW);
            paymentDao.addPP(payment);
            orderDataStore.add(new Order(userId, productIdsToString(cartDataStore.getCart(userId))));
//            Json.toJSON(userDao.find(userId), cartDataStore.getCart(userId), shippingDao.find(userId));
            cartDataStore.setCart(userId);
            resp.sendRedirect("/");
        }
        else if (Objects.equals(cardCheckbox, "on") && payPalCheckbox == null &&
                cardNumber != null && cardHolder != null &&
                expiryDate != null && cardCode!= null) {
            Payment payment = new Payment(userId, cardNumber, cardHolder, expiryDate, cardCode);
            paymentDao.addCard(payment);
            orderDataStore.add(new Order(userId, productIdsToString(cartDataStore.getCart(userId))));
//            Json.toJSON(userDao.find(userId), cartDataStore.getCart(userId), shippingDao.find(userId));
            cartDataStore.setCart(userId);
            resp.sendRedirect("/");
        }
        else {
//            context.setVariable("shipping", orderDataStore.getShipping());
            context.setVariable("user", userDao.find(userId));
            context.setVariable("shipping", shippingDao.find(userId));
            context.setVariable("priceSum", cartDataStore.getPriceSum(userId));
            context.setVariable("cart", sumOfProducts(cartDataStore.getCart(userId)));
            engine.process("product/payment.html", context, resp.getWriter());
        }

    }
    public Map<Product, Integer> sumOfProducts(List<Product> products) {
        Map<Product, Integer> prodMap = new HashMap<>();
        for (Product product: products) {
            if (prodMap.containsKey(product)) {
                prodMap.replace(product, prodMap.get(product) + 1);
            } else {
                prodMap.put(product, 1);
            }
        }
        return prodMap;
    }

    public String productIdsToString(List<Product> products) {
        String result = "";
        for (int i=0; i < products.size(); i++) {
            result += i == 0 ? products.get(i).getId() : ", " + products.get(i).getId();
        }
        return result;
    }
}
