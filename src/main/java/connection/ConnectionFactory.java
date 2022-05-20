package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ConnectionFactory makes the connection to the database
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb";
    private static final String USER = "root";
    private static final String PASS = ".punct_LINIUTA22";


    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructor of the ConnectionFactory class
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the connection
     * @return
     */

    private Connection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error occurred while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;


    }

    /**
     * Getter for the connection
     * @return
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error occurred while trying to close the connection");
            }
        }

    }

    /**
     * Closes the connection
     * @param statement
     */

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error occurred while trying to close the statement");
            }
        }

    }

    public static void close(ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error occurred while trying to close the result set");
            }
        }

    }


}
