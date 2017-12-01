package org.epam.mywebapp.Controller.Servlet;

import org.epam.mywebapp.Exeptions.UserAuthenticationException;
import org.epam.mywebapp.Model.Implements.*;
import org.epam.mywebapp.Model.Interfaces.BidDAO;
import org.epam.mywebapp.Model.Interfaces.ProductDAO;
import org.epam.mywebapp.Model.Interfaces.UserDAO;

import javax.servlet.ServletException;
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

        if(request.getParameter("findBy") == null){
            findAll(request);
        }else if(request.getParameter("findBy").equals("title")){
            String title =  request.getParameter("searchText").toString();
            findByTitle(request, title);
        }

        request.getRequestDispatcher("/General.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            String login = request.getSession().getAttribute("login").toString();
            Long productId = Long.parseLong(request.getParameter("productId"));

            UserDAO userDAO = new UserOracleDAOImpl();
            ProductDAO productDAO = new ProductOracleDAOImpl();
        try {
            if (request.getParameter("buy").equals("true")) {
                Product product = productDAO.findByUID(productId);
                productDAO.sellProduct(product);
            }else{
                    Integer count = Integer.parseInt(request.getParameter("count"));
                    User user = userDAO.findByLogin(login);
                    BidDAO bidDAO = new BidOracleDAOImpl();
                    Product product = productDAO.findByUID(productId);
                    if (count > product.getStep()) {
                        Bid bid = new Bid();
                        bid.setUserId(user.getId());
                        bid.setProductId(productId);
                        bid.setCount(count);
                        bidDAO.add(bid);
                    } else {
                        log.log(Level.SEVERE, "Bid's count is less than a step");
                    }
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, "SQL exception", e);
            } catch (UserAuthenticationException e) {
                log.log(Level.SEVERE, "User not exist", e);
            }

            doGet(request, response);
        }

    private void findByTitle(HttpServletRequest request, String title){
        ProductDAO productDAO = new ProductOracleDAOImpl();
        try {
            ArrayList<Product> products = productDAO.findByTitle(title);
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
    }
    private void findById(HttpServletRequest request){

    }
    private void findByDescription(HttpServletRequest request){

    }
    private void findAll(HttpServletRequest request){
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
    }



}
