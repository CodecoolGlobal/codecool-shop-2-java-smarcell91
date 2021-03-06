package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Product;

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
            Map<String, String> paymentInfo = new HashMap<>();
            paymentInfo.put("payPalUserName", payPalUserName);
            paymentInfo.put("payPalPW", payPalPW);
            orderDataStore.addPayment(paymentInfo);
//            orderDataStore.toJSON();
            cartDataStore.setCart(userId);
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
//            orderDataStore.toJSON();

            cartDataStore.setCart(userId);
            resp.sendRedirect("/");
        }
        else {
//            context.setVariable("shipping", orderDataStore.getShipping());
            context.setVariable("priceSum", cartDataStore.getPriceSum(userId));
            context.setVariable("cart", cartDataStore.getCart(userId));
            engine.process("product/payment.html", context, resp.getWriter());
        }
    }
}
