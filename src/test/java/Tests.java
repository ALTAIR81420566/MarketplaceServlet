
import junit.framework.Assert;
import org.epam.mywebapp.Exeptions.UserAuthenticationException;
import org.epam.mywebapp.Model.Implements.*;
import org.epam.mywebapp.Model.Interfaces.BidDAO;
import org.epam.mywebapp.Model.Interfaces.ProductDAO;
import org.epam.mywebapp.Model.Interfaces.UserDAO;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tests {

    private final String USER_SHOULD_BE_CREATED_MSG = "User should be created";
    private final String CHECK_LOGIN = "Check login";
    private final String CHECK_ADDRESS = "Check address";
    private final String CHECK_NAME = "Check name";
    private final String CHECK_PASSWORD = "Check password";
    private final String CHECK_ID = "Check ID";
    private final String CHECK_TITLE = "Check title";
    private final String CHECK_DESCRIPTION = "Check description";
    private final String CHECK_START_PRICE = "Check startPrice";
    private final String CHECK_START_BIDDING_DATE = "Check StartBiddingDate";
    private final String CHECK_TIME = "Check time";
    private final String CHECK_STEP = "Check step";
    private final String CHECK_IS_BUY_NOW = "Check isBuyNow";

    private static final String INSERT_BID_QUERY = "INSERT INTO Bids (BID_ID, PRODUCT_ID ,USER_ID, BID) VALUES (?,?,?,?)";
    private final String INSERT_USER_QUERY = "INSERT INTO Users (USER_ID, LOGIN, PASSWORD, BILLING_ADDRESS, NAME) VALUES (?,?,?,?,?)";
    private final String INSERT_PRODUCT_QUERY = "INSERT INTO Products (PRODUCT_ID, TITLE, DESCRIPTION, START_PRICE, START_BIDDING_DATE, " +
            "TIME, BID_STEP, IS_BUY_NOW, SELLER_ID) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String DELETE_USERS_QUERY = "DELETE FROM USERS";
    private static final String DELETE_PRODUCTS_QUERY = "DELETE FROM PRODUCTS";
    private static final String DELETE_BIDS_QUERY = "DELETE FROM BIDS";
    private static User testUser1;
    private static User testUser2;
    private static Product testProduct1;
    private static Product testProduct2;
    private static final double DELTA = 1e-15;

    private static Connection connection;
    private UserDAO userDAO = new UserOracleDAOImpl();
    private ProductDAO productDAO = new ProductOracleDAOImpl();
    private BidDAO bidDAO = new BidOracleDAOImpl();
    private static final String TEST_PROD1_TITLE_SUBST = "Клав";
    private static Logger log = Logger.getLogger(Tests.class.getName());
    private static String EXEPTION_SQL = "The exception is caused by an error in the SQL query";
    private static String EXEPTION_CLOSE_CONN = "The exception is caused by an error in close connection";


    @BeforeClass
    public static void beforeClass() {
        connection = MyTestConnection.getConnection();
        cleanDB();
        testUser1 = new User("Vasya007", "qwerty", "Moscow, Lenin st, 7/8", "Vasiliy");
        testUser1.setId(12L);
        testUser2 = new User("login", "89032549098q", "Saratov, Moscowskaya st. 98", "Olya");
        testUser2.setId(15L);
        testProduct1 = new Product("Клавиатура", "Очень хорошая", 500, 20);
        testProduct1.setuID(10);
        testProduct2 = new Product("Принтер", "Бла бла бла", 1500, 50);
        testProduct2.setuID(15);
    }

    @After
    public void after() {
        cleanDB();
        try {
            connection.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
        }
    }

    private void addUser(User user) {
        Connection connection = MyTestConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_USER_QUERY);
            statement.setLong(1, user.getId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getBillingAddress());
            statement.setString(5, user.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }
    }

    private void addProduct(Product product) {
        Connection connection = MyTestConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_PRODUCT_QUERY);
            statement.setLong(1, product.getuID());
            statement.setString(2, product.getTitle());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getStartPrice());
            statement.setLong(5,  product.getStartBiddingDate());
            statement.setLong(6, product.getTime());
            statement.setDouble(7, product.getStep());
            statement.setInt(8, product.isBuyNow() == true ? 1 : 0);
            statement.setLong(9, product.getSellerID());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }
    }

    private void addBid(Bid bid) {
        Connection connection = MyTestConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_BID_QUERY);
            statement.setObject(1, bid.getId());
            statement.setLong(2, bid.getProductId());
            statement.setLong(3, bid.getUserId());
            statement.setDouble(4, bid.getCount());
            statement.executeUpdate();

        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }
    }


    private static void cleanDB() {
        Connection connection = MyTestConnection.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DELETE_USERS_QUERY);
            statement.executeUpdate(DELETE_PRODUCTS_QUERY);
            statement.executeUpdate(DELETE_BIDS_QUERY);
        } catch (SQLException e) {
            log.log(Level.SEVERE, EXEPTION_SQL, e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                log.log(Level.SEVERE, EXEPTION_CLOSE_CONN, e);
            }
        }
    }

    @Test
    public void testShouldGetAllUsers() throws SQLException {

        addUser(testUser1);
        addUser(testUser2);

        ArrayList<User> allUsers = userDAO.getAll();

        Assert.assertEquals(CHECK_LOGIN, testUser1.getLogin(), allUsers.get(0).getLogin());
        Assert.assertEquals(CHECK_LOGIN, testUser2.getLogin(), allUsers.get(1).getLogin());
    }

    @Test
    public void testShouldCreateNewUser() throws SQLException, UserAuthenticationException {
        userDAO.add(testUser1);
        ArrayList<User> allUsers = userDAO.getAll();
        Assert.assertEquals(USER_SHOULD_BE_CREATED_MSG, testUser1.getLogin(), allUsers.get(0).getLogin());
    }


    @Test(expected = UserAuthenticationException.class)
    public void testShouldTryAddTwoTheSameUsers() throws SQLException, UserAuthenticationException {
        addUser(testUser2);

        userDAO.add(testUser2);
    }

    @Test
    public void testShouldGetUserByLogin() throws SQLException, UserAuthenticationException {
        addUser(testUser2);

        User user = userDAO.findByLogin(testUser2.getLogin());

        Assert.assertEquals(CHECK_LOGIN, testUser2.getLogin(), user.getLogin());
        Assert.assertEquals(CHECK_ADDRESS, testUser2.getBillingAddress(), user.getBillingAddress());
        Assert.assertEquals(CHECK_NAME, testUser2.getName(), user.getName());
        Assert.assertEquals(CHECK_PASSWORD, testUser2.getPassword(), user.getPassword());

    }

    @Test(expected = UserAuthenticationException.class)
    public void testShouldTryGetNonexistentUser() throws SQLException, UserAuthenticationException {
        User user = userDAO.findByLogin(testUser2.getLogin());
    }

    @Test
    public void testShouldUpdateUser() throws SQLException {
        addUser(testUser2);

        ArrayList<User> users = userDAO.getAll();

        User user = users.get(0);
        user.setBillingAddress(testUser1.getBillingAddress());
        user.setLogin(testUser1.getLogin());
        user.setName(testUser1.getName());
        user.setPassword(testUser1.getPassword());

        userDAO.update(testUser2.getLogin(), user);

        users = userDAO.getAll();

        user = users.get(0);

        Assert.assertEquals(CHECK_LOGIN, testUser1.getLogin(), user.getLogin());
        Assert.assertEquals(CHECK_ADDRESS, testUser1.getBillingAddress(), user.getBillingAddress());
        Assert.assertEquals(CHECK_NAME, testUser1.getName(), user.getName());
        Assert.assertEquals(CHECK_PASSWORD, testUser1.getPassword(), user.getPassword());

    }


    @Test
    public void testShouldGetAllProducts() throws SQLException {
        addProduct(testProduct1);

        ArrayList<Product> products = productDAO.getAll();

        Assert.assertEquals(CHECK_ID, testProduct1.getuID(), products.get(0).getuID());
        Assert.assertEquals(CHECK_TITLE, testProduct1.getTitle(), products.get(0).getTitle());
        Assert.assertEquals(CHECK_DESCRIPTION, testProduct1.getDescription(), products.get(0).getDescription());
        Assert.assertEquals(CHECK_START_PRICE, testProduct1.getStartPrice(), products.get(0).getStartPrice(), DELTA);
        Assert.assertEquals(CHECK_START_BIDDING_DATE, testProduct1.getStartBiddingDate(), products.get(0).getStartBiddingDate());
        Assert.assertEquals(CHECK_TIME, testProduct1.getTime(), products.get(0).getTime());
        Assert.assertEquals(CHECK_STEP, testProduct1.getStep(), products.get(0).getStep(), DELTA);
        Assert.assertEquals(CHECK_IS_BUY_NOW, testProduct1.isBuyNow(), products.get(0).isBuyNow());

    }

    @Test
    public void testShouldFindProductBySubstring() throws SQLException {
        addProduct(testProduct1);
        addProduct(testProduct2);

        ArrayList<Product> products = productDAO.findByTitle(TEST_PROD1_TITLE_SUBST);

        Assert.assertTrue("Size should be not null", products.size() > 0);

        for (Product product : products) {
            if (!product.getTitle().contains(testProduct1.getTitle())) {
                Assert.assertTrue("Found invalid title", false);
            }
        }

    }

    @Test
    public void testShouldFindProductById() throws SQLException {
        addProduct(testProduct1);
        Product product = productDAO.findByUID(testProduct1.getuID());
        Assert.assertEquals(CHECK_ID, testProduct1.getuID(), product.getuID());
    }

    @Test
    public void testShouldFindProductByUser() throws SQLException, UserAuthenticationException {
        userDAO.add(testUser1);
        User user = userDAO.findByLogin(testUser1.getLogin());
        testProduct1.setSellerID(user.getId());
        productDAO.add(testProduct1);

        ArrayList<Product> products = productDAO.findBySeller(testUser1.getName());
        Assert.assertEquals(CHECK_ID, user.getId().longValue(), products.get(0).getSellerID());
    }

    @Test
    public void testShouldAddNewProduct() throws SQLException {
        ArrayList<Product> products = productDAO.getAll();
        Assert.assertEquals(0, products.size());
        productDAO.add(testProduct2);
        products = productDAO.getAll();
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(CHECK_ID, testProduct2.getuID(), products.get(0).getuID());
        Assert.assertEquals(CHECK_TITLE, testProduct2.getTitle(), products.get(0).getTitle());
    }

    @Test
    public void testShouldGetBidByProduct() throws SQLException {
        productDAO.add(testProduct1);
        addUser(testUser1);
        Bid bid = new Bid(25, testUser1.getId(), testProduct1.getuID());
        addBid(bid);
        ArrayList<Bid> bids = bidDAO.findByProduct(testProduct1.getuID());
        for (Bid bid1 : bids) {
          //  Assert.assertEquals(CHECK_ID, testProduct1.getuID(), bid1.getProductId());
        }
    }

    @Test
    public void testShouldSellProduct() throws SQLException, UserAuthenticationException {
        addProduct(testProduct1);
        Product product = productDAO.findByUID(testProduct1.getuID());
        Assert.assertEquals(product.isSold(), false);
        productDAO.sellProduct(testProduct1);
        product = productDAO.findByUID(testProduct1.getuID());
        Assert.assertEquals(true, product.isSold());

    }

    @Test
    public void testShouldAddBid() {
        Bid bid = new Bid(25, testUser1.getId(), testProduct1.getuID());
        bid.setId(15L);
        bidDAO.add(bid);
        ArrayList<Bid> bids = bidDAO.getAll();
        for (Bid bid1 : bids) {
            Assert.assertEquals(CHECK_ID, bid.getId(), bid1.getId());
        }
    }

    @Test
    public void testShouldGetTheBestBid() {
        Bid lowBid = new Bid(25, testUser1.getId(), testProduct1.getuID());
        lowBid.setId(432L);
        Bid middleBid = new Bid(50, testUser2.getId(), testProduct1.getuID());
        middleBid.setId(9L);
        Bid bestBid = new Bid(500, testUser2.getId(), testProduct1.getuID());
        bestBid.setId(10L);

        addBid(lowBid);
        addBid(middleBid);
        addBid(bestBid);

        Bid findedBid = bidDAO.getLast(testProduct1.getuID());

        Assert.assertEquals("Check count", bestBid.getCount(), findedBid.getCount(), DELTA);
        Assert.assertEquals(CHECK_ID, bestBid.getId(), findedBid.getId());
    }


}
