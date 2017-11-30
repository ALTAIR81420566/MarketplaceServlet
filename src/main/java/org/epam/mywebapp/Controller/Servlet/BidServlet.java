package org.epam.mywebapp.Controller.Servlet;

import org.epam.mywebapp.Exeptions.UserAuthenticationException;
import org.epam.mywebapp.Model.Implements.Bid;
import org.epam.mywebapp.Model.Implements.BidOracleDAOImpl;
import org.epam.mywebapp.Model.Implements.User;
import org.epam.mywebapp.Model.Implements.UserOracleDAOImpl;
import org.epam.mywebapp.Model.Interfaces.BidDAO;
import org.epam.mywebapp.Model.Interfaces.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BidServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(BidServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getSession().getAttribute("login").toString();
        Long productId = Long.parseLong(request.getParameter("productId"));
        Integer count = Integer.parseInt(request.getParameter("count"));
        Integer step = Integer.parseInt(request.getParameter("step"));

        UserDAO userDAO = new UserOracleDAOImpl();

        try {
            User user = userDAO.findByLogin(login);
            BidDAO bidDAO = new BidOracleDAOImpl();
            Bid bid =  new Bid();
            bid.setUserId(user.getId());
            bid.setProductId(productId);
            bid.setCount(count);
            bidDAO.add(bid);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL exception", e);
        } catch (UserAuthenticationException e) {
            log.log(Level.SEVERE, "User not exist", e);
        }

    }
}
