package org.epam.mywebapp.Controller.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.epam.mywebapp.Model.Implements.Bid;
import org.epam.mywebapp.Model.Implements.BidOracleDAOImpl;
import org.epam.mywebapp.Model.Implements.Product;
import org.epam.mywebapp.Model.Implements.ProductOracleDAOImpl;
import org.epam.mywebapp.Model.Interfaces.BidDAO;
import org.epam.mywebapp.Model.Interfaces.ProductDAO;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GeneralPageServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(GeneralPageServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDAO productDAO = new ProductOracleDAOImpl();
        try {
            ArrayList<Product> products = productDAO.getAll();
            Map<Product, Bid> items = new HashMap<>();
            BidDAO bidDAO = new BidOracleDAOImpl();
            for(Product prod : products){
                Bid bestBid = bidDAO.getLast(prod.getuID());
                items.put(prod, bestBid);
            }

            request.setAttribute("products", items);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL exception", e);
        }
        request.getRequestDispatcher("/General.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/General.jsp").forward(request, response);
    }


}
