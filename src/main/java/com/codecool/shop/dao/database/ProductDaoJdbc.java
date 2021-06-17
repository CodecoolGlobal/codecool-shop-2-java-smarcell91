package com.codecool.shop.dao.database;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
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

public class ProductDaoJdbc implements ProductDao {
    private DataSource dataSource;

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        try(Connection c = dataSource.getConnection()) {
            String sql = "INSERT INTO products (name, description, default_price, category_id, supplier_id) VALUES (?, ?, ?, (SELECT categories.id FROM categories WHERE categories.name = ?), (SELECT suppliers.id FROM suppliers WHERE suppliers.name = ?))";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setString(3, product.getDefaultCurrency().toString());
            st.setFloat(4, product.getDefaultPrice());
            st.setString(5, product.getProductCategory().getName());
            st.setString(6, product.getSupplier().getName());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            product.setId(rs.getInt(1));
        } catch (SQLException e) {
            ErrorLogger.logError(ProductDaoJdbc.class, "Error while adding product: " + e.toString());
            throw new RuntimeException("Error while adding new product", e);
        }
    }

    @Override
    public Product find(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT products.name, products.description, default_price, category_id, supplier_id, currency, categories.name, categories.description, categories.department, suppliers.name, suppliers.description FROM products JOIN categories ON products.category_id = categories.id JOIN suppliers ON products.supplier_id = suppliers.id WHERE products.id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Product product = new Product(rs.getString(1), rs.getFloat(3), rs.getString(6), rs.getString(2), new ProductCategory(rs.getString(7), rs.getString(9), rs.getString(8)), new Supplier(rs.getString(10), rs.getString(11)));
            product.setId(id);
            return product;
        } catch (SQLException e) {
            ErrorLogger.logError(ProductDaoJdbc.class, "Error while searching for product with id " + id + ": " + e.toString());
            throw new RuntimeException("Error while searching for product", e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT products.id, products.name, products.description, default_price, category_id, supplier_id, currency, categories.name, categories.description, categories.department, suppliers.name, suppliers.description FROM products JOIN categories ON products.category_id = categories.id JOIN suppliers ON products.supplier_id = suppliers.id";
            ResultSet rs = c.createStatement().executeQuery(sql);
            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(7), rs.getString(3), new ProductCategory(rs.getString(8), rs.getString(10), rs.getString(9)), new Supplier(rs.getString(11), rs.getString(12)));
                product.setId(rs.getInt(1));
                productList.add(product);
            }
//            Product product = new Product("valami", 4.9f, "$", "test", new ProductCategory("Clothes", "valami ez is", "meg ez is"), new Supplier("Nike", "Clothes"));
//            productList.add(product);
//            System.out.println("ITTTTTT" + product.getName());
            return productList;
        } catch (SQLException e) {
            ErrorLogger.logError(ProductDaoJdbc.class, "Error while getting all products: " + e.toString());
            throw new RuntimeException("Error while getting all products", e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "DELETE FROM products WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            ErrorLogger.logError(ProductDaoJdbc.class, "Error while removing product with id " + id + ": " + e.toString());
            throw new RuntimeException("Error while removing product", e);
      }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT products.id, products.name, products.description, default_price, category_id, supplier_id, currency, categories.name, categories.description, categories.department, suppliers.name, suppliers.description FROM products JOIN categories ON products.category_id = categories.id JOIN suppliers ON products.supplier_id = suppliers.id WHERE suppliers.id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, supplier.getId());
            ResultSet rs = c.createStatement().executeQuery(sql);
            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(7), rs.getString(3), new ProductCategory(rs.getString(8), rs.getString(10), rs.getString(9)), new Supplier(rs.getString(11), rs.getString(12)));
                product.setId(rs.getInt(1));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            ErrorLogger.logError(ProductDaoJdbc.class, "Error while getting products by supplier: " + e.toString());
            throw new RuntimeException("Error while searching for product", e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT products.id, products.name, products.description, default_price, category_id, supplier_id, currency, categories.name, categories.description, categories.department, suppliers.name, suppliers.description FROM products JOIN categories ON products.category_id = categories.id JOIN suppliers ON products.supplier_id = suppliers.id WHERE categories.id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, productCategory.getId());
            ResultSet rs = c.createStatement().executeQuery(sql);
            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(7), rs.getString(3), new ProductCategory(rs.getString(8), rs.getString(10), rs.getString(9)), new Supplier(rs.getString(11), rs.getString(12)));
                product.setId(rs.getInt(1));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            ErrorLogger.logError(ShippingDaoJdbc.class, "Error while getting products by category: " + e.toString());
            throw new RuntimeException("Error while searching for product", e);
        }
    }
}