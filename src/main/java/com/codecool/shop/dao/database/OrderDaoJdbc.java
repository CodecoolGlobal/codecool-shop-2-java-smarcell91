package com.codecool.shop.dao.database;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

public class OrderDaoJdbc implements OrderDao{

    private boolean justOrdered = false;
    private DataSource dataSource;

    public OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO orders (user_id, product_ids) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, order.getUserId());
            st.setString(2, order.getProductIds());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new game state", throwables);
        }
    }

    @Override
    public Order find(int userId) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, order_date, user_id, product_ids FROM orders WHERE user_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Order order = new Order(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4));
            return order;
        } catch (SQLException e) {
            throw new RuntimeException("Error " + e);
        }
    }

    @Override
    public boolean isJustOrdered() {
        return this.justOrdered;
    }

    @Override
    public void setJustOrdered(boolean justOrdered) {
        this.justOrdered = justOrdered;
    }
}
