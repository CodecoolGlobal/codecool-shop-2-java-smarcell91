package com.codecool.shop.dao.database;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class CartDaoJdbc implements CartDao {

    private DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    @Override
//    public void add(Product product, User user) {
//        try (Connection conn = dataSource.getConnection()) {
//            String sql = "INSERT INTO cart (user_id, product_id) VALUES (?, ?)";
//            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            st.setInt(1, user.getId());
//            st.setInt(2, product.getId());
//            st.executeUpdate();
//            ResultSet rs = st.getGeneratedKeys();
//            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explaination
//            state.setId(rs.getInt(1));
//
//        } catch (SQLException throwables) {
//            throw new RuntimeException("Error while adding new game state", throwables);
//        }
//    }

    @Override
    public Product find(int id) {
        return null;
    }
    @Override
    public void remove(int id) {
        //remove
    }

    @Override
    public void decrementAmount(Product product) {
        //minus
    }

    @Override
    public Map<Product, Integer> getCart() {
        return null;
    }

    @Override
    public void setCart(Map<Product, Integer> cart) {

    }

    @Override
    public List<String> getProductsNames() {
        return null;
    }

    @Override
    public int getCartSize() {
        return 0;
    }
}
