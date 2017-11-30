
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyTestConnection {
    private static final String CONN_USER = "altair_user";
    private static final String CONN_PASSWORD = "1111";
    private static final String HOST = "localhost";
    private static final String PORT = "1521";
    private static final String SID = "XE";
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = String.format("jdbc:oracle:thin:@%s:%s:%s", HOST, PORT, SID);
    private static Logger log = Logger.getLogger(MyTestConnection.class.getName());

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, CONN_USER, CONN_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "Driver is not found", e);
        }
        return connection;
    }
}
