package com.codecool.shop.service;

import com.codecool.shop.controller.Cart;
import com.codecool.shop.model.Billing;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Shipping;
import com.codecool.shop.model.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Json {

    public static void toJSON(User user, List<Product> products, Shipping shipping) throws IOException {

        //You need to set the save folder!
        String path = "/home/marci/Asztal/";
        List<Object> toWrite = new ArrayList<>();
        toWrite.add(user);
        toWrite.add(products);
        toWrite.add(shipping);
        String fileName = user.getFirstName() + "_" + user.getLastName() + ".json";
        File file = new File(path + fileName);
        Gson gson = new Gson();
        Writer writer = Files.newBufferedWriter(Paths.get(path + fileName));
        gson.toJson(toWrite, writer);
        writer.close();
    }

    public void sendEmail() {

    }
}
