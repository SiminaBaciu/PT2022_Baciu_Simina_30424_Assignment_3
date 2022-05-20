package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import connection.ConnectionFactory;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.io.*;

/**
 * Class controller is the brain of the application
 */
public class Controller {


    private final View userInterface;
    private final ClientView clientInterface;
    private final ProductView productInterface;
    private final OrderView orderInterface;
    private final ClientBLL client;
    private final OrderBLL order;
    private final ProductBLL product;

    /**
     * Constructor for the Controller class
     */
    public Controller() {
        Connection con = ConnectionFactory.getConnection();
        if (con != null) {
            System.out.println("Connected!");
        }
        userInterface = new View();
        clientInterface = new ClientView(userInterface);
        productInterface = new ProductView(userInterface);
        orderInterface = new OrderView(userInterface);

        client = new ClientBLL();
        product = new ProductBLL();
        order = new OrderBLL();

        userInterface.setVisible(true);
        clientInterface.insertListener(new ClientInsert());
        clientInterface.deleteListener(new ClientDelete());
        clientInterface.updateListener(new ClientUpdate());
        productInterface.insertListener(new ProductInsert());
        productInterface.deleteListener(new ProductDelete());
        productInterface.updateListener(new ProductUpdate());
        orderInterface.insertListener(new OrderInsert());
        orderInterface.deleteListener(new OrderDelete());
        orderInterface.updateListener(new OrderUpdate());
        orderInterface.generateListener(new OrderBill());
        userInterface.clientView(new ViewClient());
        userInterface.productView(new ViewProduct());
        userInterface.orderView(new ViewOrder());
        userInterface.closeView(new ViewClose());
    }

    /**
     * Updates the JTables for the clients
     */

    public void updateClients() {
        JTable table = client.createTable();
        clientInterface.getScrollPane().setViewportView(table);
        clientInterface.setVisible(true);
    }
    /**
     * Updates the JTables for the orders
     */

    public void updateOrders() {
        JTable table = order.createTable();
        orderInterface.getScrollPane().setViewportView(table);
        orderInterface.setVisible(true);
    }
    /**
     * Updates the JTables for the products
     */

    public void updateProducts() {
        JTable table = product.createTable();
        productInterface.getScrollPane().setViewportView(table);
        productInterface.setVisible(true);
    }

    /**
     * Creates the ActionListener for the insert button for clients
     */

    class ClientInsert implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(clientInterface.getIdField());
            String fn = clientInterface.getFirstNameField();
            String ln = clientInterface.getLastNameField();
            String em = clientInterface.getEmailField();
            Client c = new Client(id,fn,ln,em);
            client.insertClient(c);
            updateClients();
        }
    }

    /**
     * Creates the ActionListener for the delete button for clients
     */
    class ClientDelete implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(clientInterface.getIdField());
            String fn = clientInterface.getFirstNameField();
            String ln = clientInterface.getLastNameField();
            String em = clientInterface.getEmailField();
            Client c = new Client(id,fn,ln,em);
            client.deleteClient(c);
            updateClients();
        }
    }

    /**
     * Creates the ActionListener for the update button for clients
     */
    class ClientUpdate implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(clientInterface.getIdField());
            String fn = clientInterface.getFirstNameField();
            String ln = clientInterface.getLastNameField();
            String em = clientInterface.getEmailField();
            Client c = new Client(id,fn,ln,em);
            client.updateClient(c);
            updateClients();
        }
    }

    /**
     * Creates the ActionListener for the insert button for product
     */
    class ProductInsert implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(productInterface.getIdField());
            String name = productInterface.getNameField();
            int pr = Integer.parseInt(productInterface.getPriceField());
            int st = Integer.parseInt(productInterface.getStockField());
            Product prod = new Product(id, name, pr, st);
            product.insertNewProduct(prod);
            updateProducts();
        }
    }
    /**
     * Creates the ActionListener for the delete button for products
     */
    class ProductDelete implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(productInterface.getIdField());
            String name = productInterface.getNameField();
            int pr = Integer.parseInt(productInterface.getPriceField());
            int st = Integer.parseInt(productInterface.getStockField());
            Product prod = new Product(id, name, pr, st);
            product.delete(prod);
            updateProducts();
        }
    }
    /**
     * Creates the ActionListener for the update button for products
     */
    class ProductUpdate implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(productInterface.getIdField());
            String name = productInterface.getNameField();
            int pr = Integer.parseInt(productInterface.getPriceField());
            int st = Integer.parseInt(productInterface.getStockField());
            Product prod = new Product(id, name, pr, st);
            product.update(prod);
            updateProducts();
        }
    }

    /**
     * Creates the ActionListener for the insert button for orders
     */
    class OrderInsert implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(orderInterface.getIdField());
            int clID = orderInterface.getClientID();
            int prID = orderInterface.getProductID();
            int qu = Integer.parseInt(orderInterface.getQuantity());
            Orders or = new Orders(id, clID, prID, qu);
            order.insert(or);
            updateOrders();
        }
    }

    /**
     * Creates the ActionListener for the delete button for orders
     */
    class OrderDelete implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(orderInterface.getIdField());
            int clID = orderInterface.getClientID();
            int prID = orderInterface.getProductID();
            int qu = Integer.parseInt(orderInterface.getQuantity());
            Orders or = new Orders(id, clID, prID, qu);
            order.delete(or);
            updateOrders();
        }
    }
    /**
     * Creates the ActionListener for the update button for orders
     */
    class OrderUpdate implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(orderInterface.getIdField());
            int clID = orderInterface.getClientID();
            int prID = orderInterface.getProductID();
            int qu = Integer.parseInt(orderInterface.getQuantity());
            Orders or = new Orders(id, clID, prID, qu);
            order.update(or);
            updateOrders();
        }
    }

    class OrderBill implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Orders currentOrder = order.findById(Integer.parseInt(orderInterface.getIdField()));
            int clientID = currentOrder.getClientID();
            int productID= currentOrder.getProductID();
            Client clients = client.findClientByID(clientID);
            Product products = product.searchByID(productID);

            try {
                FileWriter myWriter = new FileWriter("Order"+currentOrder.getId()+".txt");
                myWriter.write("Order no."+currentOrder.getId()+"\nProduct: "+products.getName()+"\nClient no. :"+clients.getID()+"\nTotal price: "+(currentOrder.getQuantity()*products.getPrice()));
                myWriter.close();
            } catch (IOException o) {
                System.out.println("An error occurred.");
                o.printStackTrace();
            }

        }
    }

    /**
     * Creates the ActionListener for the view clients button for the main view
     */
    class ViewClient implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            userInterface.setVisible(false);
            updateClients();
        }
    }

    /**
     * Creates the ActionListener for the view products button for the main view
     */
    class ViewProduct implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            userInterface.setVisible(false);
            updateProducts();
        }
    }
    /**
     * Creates the ActionListener for the view orders button for the main view
     */
    class ViewOrder implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            userInterface.setVisible(false);
            int[] items;
            int[] items2;
            orderInterface.getComboBox().removeAllItems();
            orderInterface.getComboBox2().removeAllItems();
            items = order.getClientsID();
            items2 = order.getProductsID();
            for (int item : items) {
                orderInterface.getComboBox().addItem(item);
            }
            for (int j : items2) {
                orderInterface.getComboBox2().addItem(j);
            }
            updateOrders();
        }
    }
    /**
     * Creates the ActionListener for closing the application
     */
    class ViewClose implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

}
