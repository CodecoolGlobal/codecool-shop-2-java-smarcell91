package com.codecool.shop.dao.database;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;

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
    public void add(Product product, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            String table = "create table IF NOT EXISTS cart"+userId+" (" + "product_id INT," + ")";

            String sql = "INSERT INTO ? (product_id) VALUES (?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.execute(table);
            st.setString(1, "cart"+userId);
            st.setInt(2, product.getId());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding "+product.getId()+" product to cart"+userId+" cart.", throwables);
        }
    }

    @Override
    public Product find(int productId, int userId) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT product_id FROM ? ";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "cart"+userId);
            ResultSet rs = ps.executeQuery();
            List<Product> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                // get product by id and create a product object
                Product product = new Product()  //(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7));
                product.setId(rs.getInt(1));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all players", e);
        }
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
            System.out.println(e);
        }
    }

    @Override
    public void decrementAmount(int productId, int userId) {
        try (Connection c = dataSource.getConnection()){
            String sql = "DELETE FROM ? WHERE product_id = ? LIMIT 1";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, "cart"+userId);
            st.setInt(2, productId);
            st.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Product> getCart(int userId) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT product_id FROM ? ";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "cart"+userId);
            ResultSet rs = ps.executeQuery();
            List<Product> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                // get product by id and create a product object
                Product product = new Product()  //(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7));
                product.setId(rs.getInt(1));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all players", e);
        }
    }

    @Override
    public void setCart(Map<Integer, List<Product>> cart) {

    }
}
