package com.codecool.shop.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.codecool.shop.dao.ShippingDao;
import com.codecool.shop.model.Shipping;

public class ShippingDaoJdbc implements ShippingDao {
    private DataSource dataSource;

    public ShippingDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Shipping shipping, int userId) {
        try(Connection c = dataSource.getConnection()) {
            String sql = "INSERT INTO shipping (user_id, country, city, zipcode, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userId);
            st.setString(2, shipping.getCountry());
            st.setString(3, shipping.getCity());
            st.setInt(4, shipping.getZipcode());
            st.setString(5, shipping.getAddress());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            shipping.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding new shipping", e);
        }
    }

    @Override
    public Shipping find(int userId) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, user_id, country, city, zipcode, address FROM shipping WHERE user_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Shipping shipping = new Shipping(rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(2));
            shipping.setId(rs.getInt(1));
            return shipping;
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching for shipping", e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "DELETE FROM shipping WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing shipping", e);
      }
    }
}
