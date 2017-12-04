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
    private enum Actions {ADD, EDIT};
    private Actions currentAction;
    private Long productId;
    private final String ACTION_PARAM = "action";
    private final String STEP_PARAM = "step";
    private final String EDIT_PARAM = "edit";
    private final String TITLE_PARAM = "title";
    private final String START_PRICE_PARAM = "startPrice";
    private final String DESCRIPTION_PARAM = "description";
    private final String BUY_IT_NOW_PARAM = "buyItNow";
    private final String TIME_LEFT_PARAM = "timeLeft";
    private final String PRODICT_ID_PARAM = "productId";
    private final String ADDING_PATH = "/Adding.jsp";
    private final String LOGIN_ATTR = "login";
    private final String RESP_ATTR = "resp";
    private final String SUCCES = "success";
    private final String SQL_ERR = "SQL error";
    private final String AUTH_ERR = "Authentication error";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        if(currentAction.equals(Actions.EDIT)){
            edit(request,response);
        }else{
            add(request,response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter(ACTION_PARAM).equals(EDIT_PARAM)){
            currentAction = Actions.EDIT;
            productId = Long.parseLong(request.getParameter(PRODICT_ID_PARAM));
        }else{
            currentAction = Actions.ADD;
        }
        request.getRequestDispatcher(ADDING_PATH).forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter(TITLE_PARAM);
        String description = request.getParameter(DESCRIPTION_PARAM);
        String startPrice = request.getParameter(START_PRICE_PARAM);
        int timeLeft = Integer.parseInt(request.getParameter(TIME_LEFT_PARAM));
        boolean buyItNow = Boolean.parseBoolean(request.getParameter(BUY_IT_NOW_PARAM));
        String step = request.getParameter(STEP_PARAM);

        ProductDAO productDAO = new ProductOracleDAOImpl();
        UserDAO userDAO = new UserOracleDAOImpl();

        JSONObject obj = new JSONObject();

        try {
            String login = request.getSession().getAttribute(LOGIN_ATTR).toString();

            User user = userDAO.findByLogin(login);
            Product product = new Product(title,description,Double.parseDouble(startPrice),Double.parseDouble(step));
            product.setSellerID(user.getId());
            product.setBuyNow(buyItNow);
            product.setTime(timeLeft);
            productDAO.add(product);
            obj.put(RESP_ATTR, SUCCES);
        } catch (SQLException e) {
            log.log(Level.SEVERE, SQL_ERR, e);
            obj.put(RESP_ATTR, SQL_ERR);
        } catch (UserAuthenticationException | NullPointerException e) {
            log.log(Level.SEVERE, AUTH_ERR, e);
            obj.put(RESP_ATTR, AUTH_ERR);
        }finally {
            PrintWriter writer = response.getWriter();

            writer.print(obj);
            writer.close();
        }
    }
    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter(TITLE_PARAM);
        String description = request.getParameter(DESCRIPTION_PARAM);
        String startPrice = request.getParameter(START_PRICE_PARAM);
        int timeLeft = Integer.parseInt(request.getParameter(TIME_LEFT_PARAM));
        boolean buyItNow = Boolean.parseBoolean(request.getParameter(BUY_IT_NOW_PARAM));
        String step = request.getParameter(STEP_PARAM);

        ProductDAO productDAO = new ProductOracleDAOImpl();
        UserDAO userDAO = new UserOracleDAOImpl();

        JSONObject obj = new JSONObject();

        try {
            String login = request.getSession().getAttribute(LOGIN_ATTR).toString();

            User user = userDAO.findByLogin(login);
            Product product = new Product(title,description,Double.parseDouble(startPrice),Double.parseDouble(step));
            product.setSellerID(user.getId());
            product.setBuyNow(buyItNow);
            product.setTime(timeLeft);
            product.setuID(productId);
            productDAO.update(product);
            obj.put(RESP_ATTR, SUCCES);
        } catch (SQLException e) {
            log.log(Level.SEVERE, SQL_ERR, e);
            obj.put(RESP_ATTR, SQL_ERR);
        } catch (UserAuthenticationException | NullPointerException e) {
            log.log(Level.SEVERE, AUTH_ERR, e);
            obj.put(RESP_ATTR, AUTH_ERR);
        }finally {

            PrintWriter writer = response.getWriter();

            writer.print(obj);
            writer.close();
        }
    }
}
