package org.epam.mywebapp.Model.Implements;


import org.epam.mywebapp.Controller.MyConnection;
import org.epam.mywebapp.Model.Interfaces.ProductDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductOracleDAOImpl implements ProductDAO {
    private static final String SELECT_ALL_QUERY = "SELECT * FROM PRODUCTS";
    private static final String FIND_BY_TITLE_QUERY = "SELECT * FROM PRODUCTS WHERE TITLE LIKE ?";
    private static final String SELECT_BY_UID = "SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?";;
    private static final String SELECT_BY_SELLER = "SELECT * FROM PRODUCTS INNER JOIN USERS on PRODUCTS.SELLER_ID = USERS.USER_ID WHERE " +
            "USERS.NAME = ?";
    private static final String SOLD_PRODUCT_QUERY = "UPDATE PRODUCTS SET IS_SOLD = ? WHERE PRODUCT_ID = ?";
    private final String INSERT_PRODUCT_QUERY = "INSERT INTO Products (PRODUCT_ID, TITLE, DESCRIPTION, START_PRICE, START_BIDDING_DATE, " +
            "TIME, BID_STEP, IS_BUY_NOW, SELLER_ID, IS_SOLD) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private String EXEPTION_SQL = "The exception is caused by an error in the SQL query";
    private String EXEPTION_CLOSE_CONN = "The exception is caused by an error in close connection";
    private static Logger log = Logger.getLogger(ProductOracleDAOImpl.class.getName());

    private Product createProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setuID(rs.getLong(1));
        product.setTitle(rs.getString(2));
        product.setStartPrice(rs.getInt(3));
        product.setStartBiddingDate(rs.getLong(4));
        product.setTimeMillis(rs.getLong(5));
        product.setStep(rs.getInt(6));
        product.setIsBuyNow(rs.getInt(7) > 0 ? true : false);
        product.setDescription(rs.getString(8));
        product.setSellerID(rs.getLong(9));
        product.setSold(rs.getInt(10) > 0 ? true : false);
        return product;
    }


    @Override
    public ArrayList<Product> getAll(){
        Connection conn = MyConnection.getConnection();
        Statement statement = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);
            while (rs.next()){
                products.add(createProduct(rs));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        }finally {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }

        return products;
    }

    @Override
    public ArrayList<Product> findByTitle(String title){
        Connection conn = MyConnection.getConnection();
        PreparedStatement statement = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
            statement = conn.prepareStatement(FIND_BY_TITLE_QUERY);
            statement.setString(1,"%"+title+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                products.add(createProduct(rs));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        }finally {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }

        return products;
    }

    @Override
    public Product findByUID(long uID){
        Connection conn = MyConnection.getConnection();
        PreparedStatement statement = null;
        Product product = null;
        try {
            statement = conn.prepareStatement(SELECT_BY_UID);
            statement.setLong(1, uID);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                product = createProduct(rs);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        }finally {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }
        return product;
    }

    @Override
    public ArrayList<Product> findBySeller(String sellerName){
        Connection conn = MyConnection.getConnection();
        PreparedStatement statement = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
            statement = conn.prepareStatement(SELECT_BY_SELLER);
            statement.setString(1, sellerName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                products.add(createProduct(rs));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        }finally {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }

        }

        return products;
    }

    @Override
    public void add(Product product) {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_PRODUCT_QUERY);
            statement.setObject(1,product.getuID());
            statement.setString(2,product.getTitle());
            statement.setString(3,product.getDescription());
            statement.setDouble(4,product.getStartPrice());
            statement.setLong(5,  product.getStartBiddingDate());
            statement.setLong(6,  product.getTimeMillis());
            statement.setDouble(7, product.getStep());
            statement.setInt(8,  product.isBuyNow() == true ? 1 : 0 );
            statement.setLong(9, product.getSellerID());
            statement.setInt(10,  product.isSold()  == true ? 1 : 0 );

            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        }finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }
    }

    @Override
    public void sellProduct(Product product) {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SOLD_PRODUCT_QUERY);
            statement.setInt(1,1); // TODO Here we can use NUMBER(1,0) instead of VARCHAR2
            statement.setLong(2,product.getuID());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        }finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }
    }
}
