package com.codecool.shop.dao.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.service.ErrorLogger;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {

    private DataSource dataSource;

    public ProductCategoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ProductCategory productCategory) {
        try(Connection c = dataSource.getConnection()) {
            String sql = "INSERT INTO categories (name, description, department) VALUES (?, ?, ?)";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, productCategory.getName());
            st.setString(2, productCategory.getDescription());
            st.setString(3, productCategory.getDepartment());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            productCategory.setId(rs.getInt(1));
        } catch (SQLException e) {
            ErrorLogger.logError(ProductCategoryDaoJdbc.class, "Error while adding new product category: " + e.toString());
            throw new RuntimeException("Error while adding new product category", e);
        }
    }

    @Override
    public ProductCategory find(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT name, description, department FROM categories WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ProductCategory productCategory = new ProductCategory(rs.getString(1), rs.getString(3), rs.getString(2));
            productCategory.setId(id);
            return productCategory;
        } catch (SQLException e) {
            ErrorLogger.logError(ProductCategoryDaoJdbc.class, "Error while searching for product category with id " + id + ": " + e.toString());
            throw new RuntimeException("Error while searching for product category", e);
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, department FROM categories";
            ResultSet rs = c.createStatement().executeQuery(sql);
            List<ProductCategory> productCategoryList = new ArrayList<>();
            while (rs.next()) {
                ProductCategory productCategory = new ProductCategory(rs.getString(2), rs.getString(4), rs.getString(3));
                productCategory.setId(rs.getInt(1));
                productCategoryList.add(productCategory);
            }
            return productCategoryList;
        } catch (SQLException e) {
            ErrorLogger.logError(ProductCategoryDaoJdbc.class, "Error while getting all product categories: " + e.toString());
            throw new RuntimeException("Error while getting all product categories", e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "DELETE FROM categories WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            ErrorLogger.logError(ProductCategoryDaoJdbc.class, "Error while removing product category with id " + id + ": " + e.toString());
            throw new RuntimeException("Error while removing product category", e);
      }
    }
}