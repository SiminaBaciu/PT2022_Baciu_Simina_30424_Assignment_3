package dao;

import connection.ConnectionFactory;
import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ProductDAO defines the common operations for accessing a table for the products
 */
public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO(){

    }

    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO Product (id, name, price, stock)"
            + " VALUES (?,?,?,?)";
    private static final String deleteStatementString = "DELETE FROM Product"
            + " where id = ?";
    private static final String updateStatementString = "UPDATE Product SET id = ?, name=?, price=?, stock=?"
            + " where id = ?";
    private final static String findStatementString = "SELECT * FROM Product where id = ?";

    /**
     * Find a product by ID
     * @param productID
     * @return
     */
    public Product findById(int productID) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, productID);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            int price = rs.getInt("price");
            int stock = rs.getInt("stock");

            toReturn = new Product(productID, name, price, stock);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Create a Jtable for products
     * @return
     */
    public JTable createTable(){
        List<Product> objects =findAll();
        int tableSize = objects.get(0).getClass().getDeclaredFields().length;
        DefaultTableModel table = new DefaultTableModel();
        JTable productTable = new JTable(table);
        table.addColumn("id");
        table.addColumn("name");
        table.addColumn("price");
        table.addColumn("stock");
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
        productTable.setEnabled(true);
        productTable.setVisible(true);
        return productTable;

    }

    /**
     * Insert a product
     * @param product
     * @return
     */
    public static int insert(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, product.getId());
            insertStatement.setString(2, product.getName());
            insertStatement.setInt(3, product.getPrice());
            insertStatement.setInt(4, product.getStock());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Delete a product
     * @param product
     * @return
     */
    public static int delete(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        int deletedID = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, product.getId());
            deleteStatement.executeUpdate();

            ResultSet rs = deleteStatement.getGeneratedKeys();
            if (rs.next()) {
                deletedID = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deletedID;
    }

    /**
     * Update a product
     * @param product
     * @return
     */
    public static int update(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement updateStatement = null;
        int updateID = -1;
        try {
            updateStatement = dbConnection.prepareStatement(ProductDAO.updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, product.getId());
            updateStatement.setString(2, product.getName());
            updateStatement.setInt(3, product.getPrice());
            updateStatement.setInt(4, product.getStock());
            updateStatement.setInt(5, product.getId());
            updateStatement.executeUpdate();

            ResultSet rs = updateStatement.getGeneratedKeys();
            if (rs.next()) {
                updateID = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return updateID;
    }

}
