package org.epam.mywebapp.Controller.Servlet;

import org.epam.mywebapp.Exeptions.UserAuthenticationException;
import org.epam.mywebapp.Model.Implements.User;
import org.epam.mywebapp.Model.Implements.UserOracleDAOImpl;
import org.epam.mywebapp.Model.Interfaces.UserDAO;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizationServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(AuthorizationServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

          String login = request.getParameter("login");
            String pass = request.getParameter("password");
            UserDAO userDAO = new UserOracleDAOImpl();
            JSONObject obj = new JSONObject();
            PrintWriter writer = response.getWriter();
            try {
                User user = userDAO.findByLogin(login);
                if (user.getPassword().equals(pass)) {
                    request.getSession().setAttribute("login", login);
                    request.getSession().setAttribute("role", "user");
                    obj.put("resp", "success");
                }else{
                    obj.put("resp", "Invalid password");
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, "SQL exception", e);
                obj.put("resp", "SQL exception");
            } catch (UserAuthenticationException e) {
                log.log(Level.SEVERE, "User not exist", e);
                obj.put("resp", "User with this login not exist");
            }finally {
                writer.print(obj);
                writer.close();
            }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Authorization.jsp").forward(request, response);
    }
}
