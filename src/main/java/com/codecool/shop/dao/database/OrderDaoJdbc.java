package com.codecool.shop.dao.database;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CartDaoMem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

public class OrderDaoJdbc implements OrderDao{

    private DataSource dataSource;

    public OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    void addCart(CartDaoMem cart);
    @Override
    public void addCart(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id, level) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, state.getCurrentMap());
            st.setDate(2, state.getSavedAt());
            st.setInt(3, state.getPlayer().getId());
            st.setInt(4, state.getLevel());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explaination
            state.setId(rs.getInt(1));

        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new game state", throwables);
        }
    }

    @Override
    public void addShipping(Map<String, String> shippingInfo) {

    }

    @Override
    public void addPayment(Map<String, String> paymentInfo) {

    }
}
