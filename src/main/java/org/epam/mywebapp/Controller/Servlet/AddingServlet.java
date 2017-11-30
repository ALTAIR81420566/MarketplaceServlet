package org.epam.mywebapp.Controller.Servlet;

import org.epam.mywebapp.Exeptions.UserAuthenticationException;
import org.epam.mywebapp.Model.Implements.Product;
import org.epam.mywebapp.Model.Implements.ProductOracleDAOImpl;
import org.epam.mywebapp.Model.Implements.User;
import org.epam.mywebapp.Model.Implements.UserOracleDAOImpl;
import org.epam.mywebapp.Model.Interfaces.ProductDAO;
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

public class AddingServlet  extends HttpServlet {
    private static Logger log = Logger.getLogger(AddingServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String startPrice = request.getParameter("startPrice");
        int timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
        boolean buyItNow = Boolean.parseBoolean(request.getParameter("buyItNow"));
        String step = request.getParameter("step");

        ProductDAO productDAO = new ProductOracleDAOImpl();
        UserDAO userDAO = new UserOracleDAOImpl();

        JSONObject obj = new JSONObject();

        try {
            String login = request.getSession().getAttribute("login").toString();

            User user = userDAO.findByLogin(login);
            Product product = new Product(title,description,Double.parseDouble(startPrice),Double.parseDouble(step));
            product.setSellerID(user.getId());
            product.setBuyNow(buyItNow);
            product.setTime(timeLeft);
            productDAO.add(product);
            obj.put("resp", "success");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL exception", e);
            obj.put("resp", "SQL error");
        } catch (UserAuthenticationException | NullPointerException e) {
            log.log(Level.SEVERE, "User not exist", e);
            obj.put("resp", "Authentication error");
        }finally {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = response.getWriter();

            writer.print(obj);
            writer.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/Adding.jsp").forward(request, response);
    }
    private void add(){

    }
    private void edit(){

    }
}
