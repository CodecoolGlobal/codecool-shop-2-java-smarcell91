package com.codecool.shop.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.codecool.shop.dao.BillingDao;
import com.codecool.shop.model.Billing;

public class BillingDaoJdbc implements BillingDao {
    private DataSource dataSource;

    public BillingDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Billing billing, int userId) {
        try(Connection c = dataSource.getConnection()) {
            String sql = "INSERT INTO billing (user_id, country, city, zipcode, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userId);
            st.setString(2, billing.getCountry());
            st.setString(3, billing.getCity());
            st.setInt(4, billing.getZipcode());
            st.setString(5, billing.getAddress());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            billing.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding new billing", e);
        }
    }

    @Override
    public Billing find(int userId) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, user_id, country, city, zipcode, address FROM billing WHERE user_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Billing billing = new Billing(rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
            billing.setId(rs.getInt(1));
            return billing;
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching for billing", e);
        }
    }

    @Override
    public List<Billing> getAll() {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, user_id, country, city, zipcode, address FROM billing";
            ResultSet rs = c.createStatement().executeQuery(sql);
            List<Billing> billingList = new ArrayList<>();
            while (rs.next()) {
                Billing billing = new Billing(rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
                billing.setId(rs.getInt(1));
                billingList.add(billing);
            }
            return billingList;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all billings", e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "DELETE FROM billing WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing billing", e);
      }
    }
}
