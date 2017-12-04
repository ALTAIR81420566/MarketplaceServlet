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
    private final String LOGIN = "login";
    private final String PASSWORD = "password";
    private final String ROLE = "role";
    private final String USER = "user";
    private final String RESP_ATTR = "resp";
    private final String SUCCES = "success";
    private final String GUEST = "guest";
    private final String SQL_ERR = "SQL error";
    private final String AUTHORIZATION_PATH = "Authorization.jsp";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            String login = request.getParameter(LOGIN);
            String pass = request.getParameter(PASSWORD);
            UserDAO userDAO = new UserOracleDAOImpl();
            JSONObject obj = new JSONObject();
            PrintWriter writer = response.getWriter();
            try {
                User user = userDAO.findByLogin(login);
                if (user.getPassword().equals(pass)) {
                    request.getSession().setAttribute(LOGIN, login);
                    request.getSession().setAttribute(ROLE, USER);
                    obj.put(RESP_ATTR, SUCCES);
                }else{
                    obj.put(RESP_ATTR, "Invalid password");
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, SQL_ERR, e);
                obj.put(RESP_ATTR, SQL_ERR);
            } catch (UserAuthenticationException e) {
                log.log(Level.SEVERE, "User not exist", e);
                obj.put(RESP_ATTR, "User with this login not exist");
            }finally {
                writer.print(obj);
                writer.close();
            }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(AUTHORIZATION_PATH).forward(request, response);
        request.getSession().setAttribute(LOGIN, GUEST);
        request.getSession().setAttribute(ROLE, GUEST);

    }
}
