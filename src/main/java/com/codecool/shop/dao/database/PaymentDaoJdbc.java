package com.codecool.shop.dao.database;

import com.codecool.shop.dao.PaymentDao;
import com.codecool.shop.model.Payment;
import com.codecool.shop.model.Shipping;

import javax.sql.DataSource;
import java.sql.*;

public class PaymentDaoJdbc implements PaymentDao {

    private DataSource dataSource;

    public PaymentDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addCard(Payment payment) {
        try(Connection c = dataSource.getConnection()) {
            String sql = "INSERT INTO payment (user_id, card_number, card_holder, card_date, card_code) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, payment.getUserId());
            st.setString(2, payment.getCardNumber());
            st.setString(3, payment.getCardHolder());
            st.setString(4, payment.getExpiryDate());
            st.setString(5, payment.getCardCode());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding new shipping", e);
        }
    }

    @Override
    public void addPP(Payment payment) {
        try(Connection c = dataSource.getConnection()) {
            String sql = "INSERT INTO payment (user_id, PP_user, PP_password) VALUES (?, ?, ?)";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, payment.getUserId());
            st.setString(2, payment.getPPUser());
            st.setString(3, payment.getPPPassword());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding new shipping", e);
        }
    }

    @Override
    public Payment find(int userId) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, user_id, PP_user, PP_password, card_number, card_holder, card_date, card_code FROM payment WHERE user_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Payment payment = new Payment(rs.getInt(1), rs.getInt(2), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
            return payment;
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching for shipping", e);
        }
    }
}
