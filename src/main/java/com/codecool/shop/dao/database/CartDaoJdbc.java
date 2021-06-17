package com.codecool.shop.dao.database;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;
import com.codecool.shop.service.ErrorLogger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartDaoJdbc implements CartDao {

    private DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addTable(int userId) {
        try (Connection conn = dataSource.getConnection()) {
            Statement st = conn.createStatement();
            String table = "CREATE TABLE cart"+userId+"(product_id INT)";
            st.executeUpdate(table);
        } catch (SQLException throwables) {
            ErrorLogger.logError(CartDaoJdbc.class, "Error while creating and adding new table: " + throwables.toString());
            throw new RuntimeException("Error ", throwables);
        }
    }

    @Override
    public void add(Product product, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            Statement st = conn.createStatement();
            String sql = "INSERT INTO cart"+userId+"(product_id) VALUES ("+product.getId()+")";
            st.executeUpdate(sql);
        } catch (SQLException throwables) {
            ErrorLogger.logError(CartDaoJdbc.class, "Error while adding " + product.getName() +  " product to cart: " + throwables.toString());
            throw new RuntimeException("Error while adding "+product.getId()+" product to cart"+userId+" cart.", throwables);
        }
    }

    @Override
    public Product find(int productId, int userId) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT products.name, products.description, default_price, category_id, supplier_id, currency, categories.name, categories.description, categories.department, suppliers.name, suppliers.description FROM ? c JOIN products ON c.product_id JOIN categories ON products.category_id = categories.id JOIN suppliers ON products.supplier_id = suppliers.id WHERE products.id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "cart"+userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Product product = new Product(rs.getString(1), rs.getFloat(3), rs.getString(6), rs.getString(2), new ProductCategory(rs.getString(7), rs.getString(9), rs.getString(8)), new Supplier(rs.getString(10), rs.getString(11)));
            product.setId(productId);
            return product;
        } catch (SQLException e) {
            ErrorLogger.logError(CartDaoJdbc.class, "Error while searching for product with id " + productId + ": " + e.toString());
            throw new RuntimeException("Error while reading product", e);
        }
    }

    @Override
    public float getPriceSum(int userId) {
        float maxSum = 0;
        List<Product> products = getCart(userId);
        for (Product product: products) {
            maxSum += product.getDefaultPrice();
        }
        return maxSum;
    }

    @Override
    public void remove(int productId, int userId) {
        try (Connection c = dataSource.getConnection()){
            String sql = "DELETE FROM ? WHERE product_id = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, "cart"+userId);
            st.setInt(2, productId);
            st.executeUpdate();
        } catch(SQLException e) {
            ErrorLogger.logError(CartDaoJdbc.class, "Error while removing product with id " + productId + ": " + e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void decrementAmount(int productId, int userId) {
        try (Connection c = dataSource.getConnection()){
            String sql = "SELECT products.name, products.description, default_price, category_id, supplier_id, currency, categories.name, categories.description, categories.department, suppliers.name, suppliers.description FROM ? c JOIN products ON c.product_id JOIN categories ON products.category_id = categories.id JOIN suppliers ON products.supplier_id = suppliers.id";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, "cart"+userId);
            st.executeUpdate();
        } catch(SQLException e) {
            ErrorLogger.logError(CartDaoJdbc.class, "Error while decrementing amount of product with id " + productId + ": " + e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getCart(int userId) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT products.id, products.name, products.description, default_price, category_id, supplier_id, currency, categories.name, categories.description, categories.department, suppliers.name, suppliers.description FROM cart"+userId+" cart JOIN products ON cart.product_id = products.id JOIN categories ON products.category_id = categories.id JOIN suppliers ON products.supplier_id = suppliers.id";
            ResultSet rs = c.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                // get product by id and create a product object
                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(7), rs.getString(3), new ProductCategory(rs.getString(8), rs.getString(10), rs.getString(9)), new Supplier(rs.getString(11), rs.getString(12)));
                product.setId(rs.getInt(1));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            ErrorLogger.logError(CartDaoJdbc.class, "Error while searching for cart with user id " + userId + ": " + e.toString());
            throw new RuntimeException("Error while reading", e);
        }
    }

    @Override
    public void setCart(int userId) {
        try (Connection conn = dataSource.getConnection()) {
            Statement st = conn.createStatement();
            String sql = "DELETE FROM cart"+userId;
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error ", e);
        }
    }
}
