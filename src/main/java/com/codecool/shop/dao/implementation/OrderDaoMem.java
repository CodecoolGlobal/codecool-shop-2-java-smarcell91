package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.Cart;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoMem implements OrderDao{
    private boolean justOrdered = false;
    private Map<Integer, Order> orders = new HashMap<>();

    private static OrderDaoMem instance = null;


    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

//    public void toJSON() throws IOException {
//
//        //You need to set the save folder!
//        String path = "/home/marci/Asztal/";
//
//        String fileName = shipping.get("firstName") + "_" + shipping.get("lastName") + ".json";
//        File file = new File(path + fileName);
//        Gson gson = new Gson();
//        Writer writer = Files.newBufferedWriter(Paths.get(path + fileName));
//        gson.toJson(this, writer);
//        writer.close();
//    }

//    public void sendEmail() {
//
//    }

    @Override
    public boolean isJustOrdered() {
        return justOrdered;
    }

    @Override
    public void setJustOrdered(boolean justOrdered) {
        this.justOrdered = justOrdered;
    }


    @Override
    public void add(Order order) {
        orders.put(order.getUserId(), order);
    }

    @Override
    public Order find(int userId) {
        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            if (entry.getKey() == userId) {
                return entry.getValue();
            }
        }
        return null;
    }
}
