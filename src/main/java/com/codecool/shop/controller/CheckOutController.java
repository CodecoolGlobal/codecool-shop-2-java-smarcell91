package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
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
        HttpSession session = req.getSession();
        int userId = Integer.parseInt(session.getAttribute("userId").toString());
        CartDaoMem cartDataStore = CartDaoMem.getInstance();
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phonenum");
        String shippingCountry = req.getParameter("shippingCountry");
        String shippingCity = req.getParameter("shippingCity");
        String shippingZipcode = req.getParameter("shippingZipcode");
        String shippingAddress = req.getParameter("shippingAddress");
        String billingCountry = req.getParameter("billingCountry");
        String billingCity = req.getParameter("billingCity");
        String billingZipcode = req.getParameter("billingZipcode");
        String billingAddress = req.getParameter("billingAddress");

        if (firstName != null) {
            Map<String, String> shippingInfo = new HashMap<>();
            shippingInfo.put("firstName", firstName);
            shippingInfo.put("lastName", lastName);
            shippingInfo.put("email", email);
            shippingInfo.put("phone", phone);
            shippingInfo.put("shippingCountry", shippingCountry);
            shippingInfo.put("shippingCity", shippingCity);
            shippingInfo.put("shippingZipcode", shippingZipcode);
            shippingInfo.put("shippingAddress", shippingAddress);
            shippingInfo.put("billingCountry", billingCountry);
            shippingInfo.put("billingCity", billingCity);
            shippingInfo.put("billingZipcode", billingZipcode);
            shippingInfo.put("billingAddress", billingAddress);
            orderDataStore.addShipping(shippingInfo);
            orderDataStore.addCart(cartDataStore);
            resp.sendRedirect("/payment");
        }
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cart", cartDataStore.getCart(userId));

        engine.process("product/checkout.html", context, resp.getWriter());
    }
}
