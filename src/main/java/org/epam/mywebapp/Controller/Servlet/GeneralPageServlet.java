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
    private final String FINDBY = "findBy";
    private final String TITLE_PARAM = "Title";
    private final String DESCRIPTION_PARAM = "Description";
    private final String UID_PARAM = "uId";
    private final String SEARCH_TEXT_PARAM = "searchText";
    private final String LOGIN = "login";
    private final String COUNT = "count";
    private final String BUY = "buy";
    private final String PRODUCT_ID_PARAM = "productId";
    private final String GENERAL_PATH = "/General.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProductDAO productDAO = new ProductOracleDAOImpl();
        ArrayList<Product> products = new ArrayList<>();
        try {
            if (request.getParameter(FINDBY) == null) {
                products = productDAO.getAll();
            } else {
                String reqSstr = null;
                reqSstr = request.getParameter(SEARCH_TEXT_PARAM).toString();
                if(request.getParameter(FINDBY).equals(TITLE_PARAM)){
                    products = productDAO.findByTitle(reqSstr);
                }else if (request.getParameter(FINDBY).equals(UID_PARAM)) {
                    products.add(productDAO.findByUID(Long.parseLong(reqSstr)));
                } else if (request.getParameter(FINDBY).equals(DESCRIPTION_PARAM)) {
                    products = productDAO.findByDescription(reqSstr);
                }
            }
            Map<Product, Bid> items = new HashMap<>();
            BidDAO bidDAO = new BidOracleDAOImpl();
            for(Product prod : products){
                Bid bestBid = bidDAO.getLast(prod.getuID());
                items.put(prod, bestBid);
            }
            request.setAttribute("products", items);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher(GENERAL_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            String login = request.getSession().getAttribute(LOGIN).toString();
            Long productId = Long.parseLong(request.getParameter(PRODUCT_ID_PARAM));

            UserDAO userDAO = new UserOracleDAOImpl();
            ProductDAO productDAO = new ProductOracleDAOImpl();
        try {
            if (request.getParameter(BUY).equals("true")) {
                Product product = productDAO.findByUID(productId);
                productDAO.sellProduct(product);
            }else{
                    Integer count = Integer.parseInt(request.getParameter(COUNT));
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

}
