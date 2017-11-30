package org.epam.mywebapp.Model.Implements;


import org.epam.mywebapp.Controller.MyConnection;
import org.epam.mywebapp.Exeptions.UserAuthenticationException;
import org.epam.mywebapp.Model.Interfaces.UserDAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserOracleDAOImpl implements UserDAO {

    private static final String SELECT_ALL_QUERY = "SELECT * FROM USERS";
    private static final String FIND_BY_LOGIN_QUERY ="SELECT * FROM USERS WHERE LOGIN = '%s' " ;
    private static final String UPDATE_QUERY = "UPDATE USERS SET LOGIN = ?, PASSWORD = ?, BILLING_ADDRESS = ?, NAME = ?";
    private String INSERT_QUERY = "INSERT INTO Users (USER_ID,LOGIN, PASSWORD, BILLING_ADDRESS, NAME) VALUES (?,?,?,?,?)";
    private String EXEPTION_SQL = "The exception is caused by an error in the SQL query";
    private String EXEPTION_CLOSE_CONN = "The exception is caused by an error in close connection";

    private static Logger log = Logger.getLogger(UserOracleDAOImpl.class.getName());
    @Override
    public void add(User user) throws UserAuthenticationException {
        Connection conn = MyConnection.getConnection();
        PreparedStatement statement= null;
        User user2;
        try {
            user2 = getUser(user.getLogin());
            if( user2 == null){
                statement = conn.prepareStatement(INSERT_QUERY);
                statement.setObject(1, user.getId());
                statement.setString(2,user.getLogin());
                statement.setString(3,user.getPassword());
                statement.setString(4,user.getBillingAddress());
                statement.setString(5,user.getName());
                statement.executeUpdate();
            }else{
                log.log(Level.SEVERE, "This user already exist");
                throw new UserAuthenticationException("This user already exist");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }

    }

    @Override
    public ArrayList<User> getAll(){
        Connection conn = MyConnection.getConnection();
        Statement statement;
        ArrayList<User> users =  null;
        try {
            statement = conn.createStatement();
            ResultSet set = statement.executeQuery(SELECT_ALL_QUERY);
            users = new ArrayList<>();
            while (set.next()){
                User user = new User(set.getString(2),set.getString(3),set.getString(4),set.getString(5));
                user.setId(set.getLong(1));
                users.add(user);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }

        return users;
    }

    @Override
    public User findByLogin(String login) throws UserAuthenticationException {
        User user = getUser(login);
        if (user == null){
            log.log(Level.SEVERE,"User with this login not exist");
            throw new UserAuthenticationException("User with this login not exist");
        }
        return user;
    }

    private User getUser(String login){
        Connection conn = MyConnection.getConnection();
        Statement statement = null;
        User user = null;
        try {
            statement = conn.createStatement();
            ResultSet set = statement.executeQuery(String.format(FIND_BY_LOGIN_QUERY, login));
            while(set.next()){
                user = new User(set.getString(2), set.getString(3), set.getString(4), set.getString(5));
                user.setId(set.getLong(1));
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

        return user;
    }

    @Override
    public void update(String login, User user){
        Connection conn = MyConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getBillingAddress());
            statement.setString(4,user.getName());
            statement.executeUpdate();
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

    }
}
