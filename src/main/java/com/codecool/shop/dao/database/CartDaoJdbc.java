package com.codecool.shop.dao.database;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
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
            String table = "create table IF NOT EXISTS cart"+userId+" (" + "product_id INT," + "quantity INT," +")";

            String sql = """
                        GO
                        IF NOT EXISTS ( select 1 from ? where product_id = ? )
                        BEGIN
                        INSERT INTO ?(product_id, qunatity) VALUES (?, 0);
                        END
                        ELSE
                        UPDATE ? SET quantity = quantity + 1 WHERE product_id = ?
                        GO""";
            PreparedStatement st = conn.prepareStatement(sql);
            st.execute(table);
            st.setString(1, "cart"+userId);
            st.setInt(2, product.getId());
            st.setString(3, "cart"+userId);
            st.setInt(4, product.getId());
            st.setString(5, "cart"+userId);
            st.setInt(6, product.getId());
            st.executeUpdate();
        } catch (SQLException throwables) {
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
            String sql = "SELECT products.name, products.description, default_price, category_id, supplier_id, currency, categories.name, categories.description, categories.department, suppliers.name, suppliers.description FROM ? c JOIN products ON c.product_id JOIN categories ON products.category_id = categories.id JOIN suppliers ON products.supplier_id = suppliers.id";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, "cart"+userId);
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
