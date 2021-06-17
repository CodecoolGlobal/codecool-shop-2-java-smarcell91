package com.codecool.shop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ErrorLogger {    
    public static void logError(Class<?> c, String error) {
        Logger logger = LoggerFactory.getLogger(c);
        logger.error(error);
    }
}
