package bll;

import dao.ClientDAO;
import model.Client;
import presentation.validators.ClientValidator;

import javax.swing.*;
import java.util.NoSuchElementException;

/**
 * Class ClientBLL encapsulate the application logic
 */
public class ClientBLL {

    ClientDAO clientDAO;
    ClientValidator clientValidator;


    public ClientBLL() {
        clientDAO = new ClientDAO();
        clientValidator = new ClientValidator();
    }

    /**
     * Calls for the creation of the client table
     * @return
     */
    public JTable createTable() {
        return clientDAO.createTable();
    }

    /**
     * Calls the find by id function after verifying the client
     * @param ID
     * @return
     */
    public Client findClientByID(int ID) {

        Client cl = clientDAO.findById(ID);
        if (cl == null) {
            throw new NoSuchElementException("The client with id =" + ID + " was not found!");
        }
        return cl;
    }

    /**
     * Calls the insert function after verifying the client
     * @param client
     * @return
     */
    public int insertClient(Client client) {
        int clientID = 0;
        try {
            clientID = client.getID();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ClientValidator clientValid = new ClientValidator();
        Client newClient = client;

        if (clientValid.validateClient(newClient)) {
            return ClientDAO.insert(newClient);
        } else
            JOptionPane.showMessageDialog(null, "Bad input!");

        return clientID;
    }

    /**
     * Calls the delete function after verifying the client
     * @param client
     * @return
     */
    public int deleteClient(Client client) {
        int clientID = 0;
        try {
            clientID = client.getID();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Client clientDelete = clientDAO.findById(clientID);
        if (clientDelete != null) {
            return ClientDAO.delete(clientDelete);
        } else
            JOptionPane.showMessageDialog(null, " Error deleting client, client does not exist. ");
        return clientID;
    }

    /**
     * Calls the update function after verifying the client
     * @param client
     */
    public void updateClient(Client client) {
        int clientID = 0;
        try {
            clientID = client.getID();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Client clientUpdate = new Client(clientID, client.getFirstName(), client.getLastName(), client.getEmail());
        if (clientValidator.validateClient(clientUpdate) == true) {
            Client newClient = findClientByID(clientID);
            newClient.setFirstName(client.getFirstName());
            newClient.setLastName(client.getLastName());
            newClient.setEmail(client.getEmail());
            ClientDAO.update(newClient);
        }

    }
}








