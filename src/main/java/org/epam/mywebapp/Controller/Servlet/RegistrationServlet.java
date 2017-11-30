package org.epam.mywebapp.Controller.Servlet;

import org.epam.mywebapp.Exeptions.UserAuthenticationException;
import org.epam.mywebapp.Model.Implements.User;
import org.epam.mywebapp.Model.Implements.UserOracleDAOImpl;
import org.epam.mywebapp.Model.Interfaces.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(RegistrationServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        UserDAO userDAO = new UserOracleDAOImpl();
        User respUser = null;
        try {
            User user =  userDAO.findByLogin(login);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL exception", e);
        } catch (UserAuthenticationException e) {
            log.log(Level.SEVERE, "User not exist");
            String pass = request.getParameter("password");
            String address = request.getParameter("address");
            String fullName = request.getParameter("fullName");
            respUser = new User(login,pass,address,fullName);
            try {
                userDAO.add(respUser);
            } catch (SQLException e1) {
                log.log(Level.SEVERE, "SQL exception", e);
            } catch (UserAuthenticationException e1) {
                log.log(Level.SEVERE, "Login already exist", e);
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(respUser);
    }
}