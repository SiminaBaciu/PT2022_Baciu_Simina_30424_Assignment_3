package dao;

import connection.ConnectionFactory;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class OrderDAO defines the common operations for accessing a table for the orders
 */
public class OrderDAO extends AbstractDAO<Orders> {

    public OrderDAO(){

    }

    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO Orders (id, clientID, productID, quantity)"
            + " VALUES (?,?,?,?)";
    private static final String deleteStatementString = "DELETE FROM Orders"
            + " where id = ?";
    private static final String updateStatementString = "UPDATE Orders SET id = ?, clientID=?, productID=?, quantity=?"
            + " where id = ?";
    private final static String findStatementString = "SELECT * FROM Orders where id = ?";

    /**
     * Find an order by ID
     * @param ordersID
     * @return
     */
    public Orders findById(int ordersID) {
        Orders toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, ordersID);
            rs = findStatement.executeQuery();
            rs.next();

            int clientID = rs.getInt("clientID");
            int productID = rs.getInt("productID");
            int quantity = rs.getInt("quantity");

            toReturn = new Orders(ordersID, clientID, productID, quantity);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OrderDao:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Create a Jtable for orders
     * @return
     */
    public JTable createTable(){
        List<Orders> objects =findAll();
        int tableSize = objects.get(0).getClass().getDeclaredFields().length;
        DefaultTableModel table = new DefaultTableModel();
        JTable orderTable = new JTable(table);
        table.addColumn("id");
        table.addColumn("clientID");
        table.addColumn("productID");
        table.addColumn("quantity");
        for (Object o : objects) {
            Object[] obj = new Object[tableSize];
            int col = 0;
            for (Field currentField : o.getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                try {
                    obj[col] = currentField.get(o);
                    col++;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            table.addRow(obj);
        }
        orderTable.setEnabled(true);
        orderTable.setVisible(true);
        return orderTable;

    }

    /**
     * Insert an order
     * @param orders
     * @return
     */
    public static int insert(Orders orders) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, orders.getId());
            insertStatement.setInt(2, orders.getClientID());
            insertStatement.setInt(3, orders.getProductID());
            insertStatement.setInt(4, orders.getQuantity());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Delete an order
     * @param orders
     * @return
     */
    public static int delete(Orders orders) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        int deletedID = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, orders.getId());
            deleteStatement.executeUpdate();

            ResultSet rs = deleteStatement.getGeneratedKeys();
            if (rs.next()) {
                deletedID = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deletedID;
    }

    /**
     * Update an order
     * @param orders
     * @return
     */
    public static int update(Orders orders) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement updateStatement = null;
        int updateID = -1;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, orders.getId());
            updateStatement.setInt(2, orders.getClientID());
            updateStatement.setInt(3, orders.getProductID());
            updateStatement.setInt(4, orders.getQuantity());
            updateStatement.setInt(5, orders.getId());
            updateStatement.executeUpdate();

            ResultSet rs = updateStatement.getGeneratedKeys();
            if (rs.next()) {
                updateID = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return updateID;
    }
}
