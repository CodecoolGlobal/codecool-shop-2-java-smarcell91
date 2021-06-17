package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Billing;
import com.codecool.shop.model.Shipping;
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
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"} )
public class CheckOutController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoManager daoManager = DaoManager.getInstance();
        HttpSession session = req.getSession();
        CartDao cartDataStore = daoManager.getCartDao();
        OrderDao orderDataStore = daoManager.getOrderDao();
        BillingDao billingDao = daoManager.getBillingDao();
        ShippingDao shippingDao = daoManager.getShippingDao();

//        String firstName = req.getParameter("firstName");
//        String lastName = req.getParameter("lastName");
//        String email = req.getParameter("email");
//        String phone = req.getParameter("phonenum");
        String shippingCountry = req.getParameter("shippingCountry");
        String shippingCity = req.getParameter("shippingCity");
        String shippingZipcode = req.getParameter("shippingZipcode");
        String shippingAddress = req.getParameter("shippingAddress");
        String billingCountry = req.getParameter("billingCountry");
        String billingCity = req.getParameter("billingCity");
        String billingZipcode = req.getParameter("billingZipcode");
        String billingAddress = req.getParameter("billingAddress");

        if (shippingCountry != null) {
            int userId = Integer.parseInt(session.getAttribute("userId").toString());
//            shippingInfo.put("firstName", firstName);
//            shippingInfo.put("lastName", lastName);
//            shippingInfo.put("email", email);
//            shippingInfo.put("phone", phone);
            Shipping shipping = new Shipping(shippingCountry, shippingCity, Integer.parseInt(shippingZipcode), shippingAddress, userId);
            shippingDao.update(shipping);

            Billing billing = new Billing(billingCountry, billingCity, Integer.parseInt(billingZipcode), billingAddress, userId);
            billingDao.update(billing);
//            orderDataStore.addCart(cartDataStore);
            resp.sendRedirect("/payment");
        }
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        if (session.getAttribute("userId") != null) {
            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            context.setVariable("billing", billingDao.find(userId));
            context.setVariable("shipping", shippingDao.find(userId));
            context.setVariable("cart", cartDataStore.getCart(userId));
        }

        engine.process("product/checkout.html", context, resp.getWriter());
    }
}
