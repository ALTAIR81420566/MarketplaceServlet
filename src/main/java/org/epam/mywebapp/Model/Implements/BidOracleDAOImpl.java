package org.epam.mywebapp.Model.Implements;


import org.epam.mywebapp.Controller.MyConnection;
import org.epam.mywebapp.Model.Interfaces.BidDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BidOracleDAOImpl implements BidDAO {
    private static final String SELECT_BY_PRODUCT_QUERY = "SELECT * FROM BIDS INNER JOIN PRODUCTS ON BIDS.PRODUCT_ID = ?";
    private static final String INSERT_QUERY = "INSERT INTO BIDS VALUES (?,?,?,?)";
    private String SELECT_ALL_QUERY = "SELECT * FROM BIDS";
    private String SELECT_BEST_QUERY = "SELECT b.BID_ID, b.USER_ID, b.PRODUCT_ID, b.BID  FROM BIDS b, " +
            "(SELECT MAX(BID) AS max, PRODUCT_ID FROM BIDS WHERE PRODUCT_ID = ? GROUP BY PRODUCT_ID) " +
            "maxresults WHERE b.PRODUCT_ID = ? AND b.BID  = maxresults.max";
    private String EXEPTION_SQL = "The exception is caused by an error in the SQL query";
    private String EXEPTION_CLOSE_CONN = "The exception is caused by an error in close connection";
    private static Logger log = Logger.getLogger(BidOracleDAOImpl.class.getName());

    private Bid createBid(ResultSet rs) throws SQLException {
        Bid bid = new Bid();
        bid.setId(rs.getLong(1));
        bid.setUserId(rs.getLong(2));
        bid.setProductId(rs.getLong(3));
        bid.setCount(rs.getInt(4));
        return bid;
    }
    @Override
    public ArrayList<Bid> findByProduct(long productId){
        Connection conn = MyConnection.getConnection();
        PreparedStatement statement = null;
        ArrayList<Bid> bids = new ArrayList<>();;
        try {
            statement = conn.prepareStatement(SELECT_BY_PRODUCT_QUERY);
            statement.setLong(1, productId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                bids.add(createBid(rs));
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

        return bids;
    }
    @Override
    public void add(Bid bid) {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_QUERY);
            statement.setObject(1, bid.getId());
            statement.setLong(2,bid.getProductId());
            statement.setLong(3,bid.getUserId());
            statement.setDouble(4,bid.getCount());
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
    public Bid getLast(long productId) {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = null;
        Bid bid = null;
        try {
            statement = connection.prepareStatement(SELECT_BEST_QUERY);
            statement.setLong(1, productId);
            statement.setLong(2, productId);
            ResultSet set = statement.executeQuery();
            set.next();
            bid = createBid(set);

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
        return bid;
    }

    @Override
    public ArrayList<Bid> getAll() {
        Connection connection = MyConnection.getConnection();
        Statement statement  = null;
        ArrayList<Bid> bids = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SELECT_ALL_QUERY);
            while (set.next()){
                bids.add(createBid(set));
            }
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

        return bids;
    }
}
