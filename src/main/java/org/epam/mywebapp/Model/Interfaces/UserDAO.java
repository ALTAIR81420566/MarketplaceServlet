package org.epam.mywebapp.Model.Interfaces;


import org.epam.mywebapp.Exeptions.UserAuthenticationException;
import org.epam.mywebapp.Model.Implements.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO{
    void add(User user) throws SQLException, UserAuthenticationException;

    ArrayList<User> getAll() throws SQLException;

    User findByLogin(String login) throws SQLException, UserAuthenticationException;

    void update(String login, User user) throws SQLException;
}
