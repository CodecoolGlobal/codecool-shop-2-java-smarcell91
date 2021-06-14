package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.Cart;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class OrderDaoMem implements OrderDao{
    public boolean justOrdered = false;
    CartDaoMem cart;
    private Map<String, String> shipping = new HashMap<>();
    private Map<String, String> payment = new HashMap<>();
    private static OrderDaoMem instance = null;


    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    public void toJSON() throws IOException {

        //You need to set the save folder!
        String path = "/home/marci/Asztal/";

        String fileName = shipping.get("firstName") + "_" + shipping.get("lastName") + ".json";
        File file = new File(path + fileName);
        Gson gson = new Gson();
        Writer writer = Files.newBufferedWriter(Paths.get(path + fileName));
        gson.toJson(this, writer);
        writer.close();
    }

//    public void sendEmail() {
//
//    }

    public CartDaoMem getCart() {
        return cart;
    }

    public Map<String, String> getShipping() {
        return shipping;
    }

    public Map<String, String> getPayment() { return payment; }

    @Override
    public void addCart(CartDaoMem cart) {
        this.cart = cart;
    }

    @Override
    public void addShipping(Map<String, String> shippingInfo) {
        this.shipping = shippingInfo;
    }

    @Override
    public void addPayment(Map<String, String> paymentInfo) {
        this.payment = paymentInfo;
        this.justOrdered = true;
    }
}
