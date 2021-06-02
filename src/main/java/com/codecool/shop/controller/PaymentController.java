package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = {"/payment"} )
public class PaymentController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDaoMem cartDataStore = CartDaoMem.getInstance();
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();

        String payPalUserName = req.getParameter("payPalUsername");
        String payPalPW = req.getParameter("payPalPW");
        String cardNumber = req.getParameter("cardNumber");
        String cardHolder = req.getParameter("cardHolder");
        String expiryDate = req.getParameter("expiryDate");
        String cardCode = req.getParameter("cardCode");
        String payPalCheckbox = req.getParameter("payPalCheckbox");
        String cardCheckbox = req.getParameter("cardCheckbox");
        System.out.println(payPalCheckbox);
        System.out.println(cardCheckbox);
        System.out.println("PPPW:" + payPalPW);
        System.out.println("PPUN:" + payPalUserName);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cart", cartDataStore.getCart());

        if (Objects.equals(payPalCheckbox, "on") && cardCheckbox == null &&
                payPalUserName != null && payPalPW != null) {
            Map<String, String> paymentInfo = new HashMap<>();
            paymentInfo.put("payPalUserName", payPalUserName);
            paymentInfo.put("payPalPW", payPalPW);
            orderDataStore.addPayment(paymentInfo);
            System.out.println("shipping:" + orderDataStore.getShipping());
            System.out.println("cart:" + orderDataStore.getCart().getCart());
            System.out.println("paymentPP:" + orderDataStore.getPayment());
            resp.sendRedirect("/");
        }
        else if (Objects.equals(cardCheckbox, "on") && payPalCheckbox == null &&
                cardNumber != null && cardHolder != null &&
                expiryDate != null && cardCode!= null) {
            Map<String, String> paymentInfo = new HashMap<>();
            paymentInfo.put("cardNumber", cardNumber);
            paymentInfo.put("cardHolder", cardHolder);
            paymentInfo.put("expiryDate", expiryDate);
            paymentInfo.put("cardCode", cardCode);
            orderDataStore.addPayment(paymentInfo);
            System.out.println("shipping:" + orderDataStore.getShipping());
            System.out.println("cart:" + orderDataStore.getCart().getCart());
            System.out.println("paymentCC:" + orderDataStore.getPayment());
            resp.sendRedirect("/");
        }
        else {
            engine.process("product/payment.html", context, resp.getWriter());
        }
    }
}
