package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.BillingDao;
import com.codecool.shop.dao.DaoManager;
import com.codecool.shop.dao.ShippingDao;
import com.codecool.shop.model.Billing;
import com.codecool.shop.model.Shipping;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet(name = "billingController", urlPatterns = {"/billinginfo"})
public class BillingController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        DaoManager daoManager = DaoManager.getInstance();
        daoManager.setup();
        HttpSession session = req.getSession();
        int userId = (int)session.getAttribute("userId");
        BillingDao billingDataStore = daoManager.getBillingDao();
        ShippingDao shippingDataStore = daoManager.getShippingDao();

        String shippingCountry = req.getParameter("shippingCountry");
        String shippingCity = req.getParameter("shippingCity");
        String shippingZipcode = req.getParameter("shippingZipcode");
        String shippingAddress = req.getParameter("shippingAddress");
        String billingCountry = req.getParameter("billingCountry");
        String billingCity = req.getParameter("billingCity");
        String billingZipcode = req.getParameter("billingZipcode");
        String billingAddress = req.getParameter("billingAddress");
        Billing billing = new Billing(billingCountry, billingCity, Integer.valueOf(billingZipcode), billingAddress, userId);
        Shipping shipping = new Shipping(shippingCountry, shippingCity, Integer.valueOf(shippingZipcode), shippingAddress, userId);
        billingDataStore.add(billing, userId);
        shippingDataStore.add(shipping, userId);
        response.sendRedirect("/");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, response, req.getServletContext());
        engine.process("product/index.html", context, response.getWriter());
    }
}
