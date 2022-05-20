package dao;


import connection.ConnectionFactory;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ClientDAO defines the common operations for accessing a table for the Clients
 */
public class ClientDAO extends AbstractDAO<Client>{

    public ClientDAO(){

    }

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO Client (ID, firstName, lastName, age, email)"
            + " VALUES (?,?,?,0,?)";
    private static final String deleteStatementString = "DELETE FROM Client"
            + " where ID = ?";
    private static final String updateStatementString = "UPDATE Client SET ID = ?, firstName=?, lastName=?, age=0, email=?"
            + " where ID = ?";
    private final static String findStatementString = "SELECT * FROM Client where ID = ?";

    /**
     * Find a client by their ID
     * @param clientID
     * @return
     */
    public Client findById(int clientID) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, clientID);
            rs = findStatement.executeQuery();
            rs.next();

            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String email = rs.getString("email");

            toReturn = new Client(clientID, firstName, lastName, email);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDao:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Create a Jtable for clients
     * @return
     */
    public JTable createTable(){
        List<Client> objects =findAll();
        int tableSize = objects.get(0).getClass().getDeclaredFields().length;
        DefaultTableModel table = new DefaultTableModel();
        JTable clientTable = new JTable(table);
        table.addColumn("ID");
        table.addColumn("first name");
        table.addColumn("last name");
        table.addColumn("email");
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
        clientTable.setEnabled(true);
        clientTable.setVisible(true);
        return clientTable;

    }
    /**
     * Insert a client
     * @param client
     * @return
     */
    public static int insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, client.getID());
            insertStatement.setString(2, client.getFirstName());
            insertStatement.setString(3, client.getLastName());
            insertStatement.setString(4, client.getEmail());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Delete a client
     * @param client
     * @return
     */
    public static int delete(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        int deletedID = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, client.getID());
            deleteStatement.executeUpdate();

            ResultSet rs = deleteStatement.getGeneratedKeys();
            if (rs.next()) {
                deletedID = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deletedID;
    }

    /**
     * Update a client
     * @param client
     * @return
     */
    public static int update(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement updateStatement = null;
        int updateID = -1;
        try {
            updateStatement = dbConnection.prepareStatement(ClientDAO.updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, client.getID());
            updateStatement.setString(2, client.getFirstName());
            updateStatement.setString(3, client.getLastName());
            updateStatement.setString(4, client.getEmail());
            updateStatement.setInt(5, client.getID());
            updateStatement.executeUpdate();

            ResultSet rs = updateStatement.getGeneratedKeys();
            if (rs.next()) {
                updateID = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return updateID;
    }

}
