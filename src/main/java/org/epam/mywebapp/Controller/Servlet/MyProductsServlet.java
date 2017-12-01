package org.epam.mywebapp.Controller.Servlet;

import org.epam.mywebapp.Model.Implements.Bid;
import org.epam.mywebapp.Model.Implements.BidOracleDAOImpl;
import org.epam.mywebapp.Model.Implements.Product;
import org.epam.mywebapp.Model.Implements.ProductOracleDAOImpl;
import org.epam.mywebapp.Model.Interfaces.BidDAO;
import org.epam.mywebapp.Model.Interfaces.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDAO productDAO = new ProductOracleDAOImpl();
        String login = request.getSession().getAttribute("login").toString();
        ArrayList<Product> products = productDAO.findBySellerLogin(login);
        Map<Product, Bid> items = new HashMap<>();
        BidDAO bidDAO = new BidOracleDAOImpl();
        for(Product prod : products){
            Bid bestBid = bidDAO.getLast(prod.getuID());
            items.put(prod, bestBid);
        }
        request.setAttribute("products", items);
        request.getRequestDispatcher("/MyProducts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.parseLong(request.getParameter("productId"));
        ProductDAO productDAO = new ProductOracleDAOImpl();
        productDAO.delete(productId);
        doGet(request,response);
    }
}
