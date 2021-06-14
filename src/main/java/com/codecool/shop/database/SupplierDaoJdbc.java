package com.codecool.shop.database;

import com.codecool.shop.dao.SupplierDao;

import javax.sql.DataSource;

public class SupplierDaoJdbc implements SupplierDao {

    private DataSource dataSource;

    public SupplierDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
