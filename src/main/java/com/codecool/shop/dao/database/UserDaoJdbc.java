package com.codecool.shop.dao.database;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc implements UserDao{

    private DataSource dataSource;

    public UserDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void add(User user) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, email, pw_hash) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPwHash());
            st.executeUpdate();

        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new game state", throwables);
        }
    }


    @Override
    public User find(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, first_name, last_name, email, pw_hash FROM users WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting game state with id: " + id + e);
        }
    }

    public void remove(int id) {
        try (Connection c = dataSource.getConnection()){
        String sql = "DELETE FROM Table WHERE name = ?";
        PreparedStatement st = c.prepareStatement(sql);
        st.setInt(1, id);
        st.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    public List<User> allUser() {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT id, first_name, last_name, email, pw_hash FROM users";
            ResultSet rs = c.createStatement().executeQuery(sql);
            List<User> result = new ArrayList<>();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all game states", e);
        }
    }
}
