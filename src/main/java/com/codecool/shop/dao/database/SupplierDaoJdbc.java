package com.codecool.shop.dao.database;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ErrorLogger;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {
    private DataSource dataSource;

    public SupplierDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Supplier supplier) {
        try(Connection c = dataSource.getConnection()) {
            String sql = "INSERT INTO suppliers (name, description) VALUES (?, ?)";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, supplier.getName());
            st.setString(2, supplier.getDescription());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            supplier.setId(rs.getInt(1));
        } catch (SQLException e) {
            ErrorLogger.logError(SupplierDaoJdbc.class, "Error while adding supplier: " + e.toString());
            throw new RuntimeException("Error while adding new supplier", e);
        }
    }

    @Override
    public Supplier find(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT name, description FROM suppliers WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Supplier supplier = new Supplier(rs.getString(1), rs.getString(2));
            supplier.setId(id);
            return supplier;
        } catch (SQLException e) {
            ErrorLogger.logError(SupplierDaoJdbc.class, "Error while searching for supplier with id " + id + ": " + e.toString());
            throw new RuntimeException("Error while searching for supplier", e);
        }
    }

    @Override
    public List<Supplier> getAll() {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM suppliers";
            ResultSet rs = c.createStatement().executeQuery(sql);
            List<Supplier> supplierList = new ArrayList<>();
            while (rs.next()) {
                Supplier supplier = new Supplier(rs.getString(2), rs.getString(3));
                supplier.setId(rs.getInt(1));
                supplierList.add(supplier);
            }
            return supplierList;
        } catch (SQLException e) {
            ErrorLogger.logError(SupplierDaoJdbc.class, "Error while getting all suppliers: " + e.toString());
            throw new RuntimeException("Error while getting all suppliers", e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "DELETE FROM suppliers WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            ErrorLogger.logError(SupplierDaoJdbc.class, "Error while removing supplier with id " + id + ": " + e.toString());
            throw new RuntimeException("Error while removing supplier", e);
      }
    }
}