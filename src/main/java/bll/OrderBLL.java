package bll;

import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.util.List;

/**
 * Class OrderBLL encapsulate the application logic
 */
public class OrderBLL {

    OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }


    public List<Orders> getOrdersList() {
        return orderDAO.findAll();
    }
    /**
     * Calls for the creation of the order table
     * @return
     */
    public JTable createTable() {
        return orderDAO.createTable();
    }

    /**
     * Makes a list of all the clients IDs
     * @return
     */
    public int[] getClientsID() {
        int[] result;
        ClientDAO cDAO = new ClientDAO();
        List<Client> list1 = cDAO.findAll();
        result = new int[list1.size()];
        int i = 0;
        for (Client c : list1) {
            result[i] = c.getID();
            i++;
        }
        return result;
    }
    /**
     * Makes a list of all the products IDs
     * @return
     */
    public int[] getProductsID() {
        int[] result;
        ProductDAO cDAO = new ProductDAO();
        List<Product> list1 = cDAO.findAll();
        result = new int[list1.size()];
        int i = 0;
        for (Product c : list1) {
            result[i] = c.getId();
            i++;
        }
        return result;
    }

    /**
     * Calls the insert function after verifying the orders and updates the stock and quantity
     * @param order
     */
    public void insert(Orders order) {
        int orderID = 0;
        try {
            orderID = order.getId();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ProductDAO productDAO = new ProductDAO();
        Product newProduct = productDAO.findById(order.getProductID());

        if (order.getQuantity() < newProduct.getStock()) {
            newProduct.setStock(newProduct.getStock() - order.getQuantity());
            ProductDAO.update(newProduct);

            Orders newOrder = order;
            OrderDAO.insert(newOrder);
        } else {
            JOptionPane.showMessageDialog(null, "Not enough products!");

        }
    }

    /**
     *  Calls the delete function after verifying the orders and updates the stock and quantity
     * @param order
     */
    public void delete(Orders order) {
        int orderID = 0;
        try {
            orderID = order.getId();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Orders oDelete = orderDAO.findById(order.getId());
        if (oDelete != null) {
            int productID = oDelete.getProductID();
            int quantity = oDelete.getQuantity();
            ProductDAO prDAO = new ProductDAO();
            Product myProduct = prDAO.findById(productID);
            int productStock = myProduct.getStock();
            myProduct.setStock(productStock + quantity);
            ProductDAO.update(myProduct);
            OrderDAO.delete(oDelete);
        } else
            JOptionPane.showMessageDialog(null, "Error when deleting order. Order inexistent! ");
    }

    /**
     *  Calls the update function after verifying the orders and updates the stock and quantity
     * @param order
     */
    public void update(Orders order) {
        int idU, quan;
        try {
            idU = order.getId();
            quan = order.getQuantity();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        Orders orderToBeUpdated = orderDAO.findById(idU);
        int productID = orderToBeUpdated.getProductID();
        int clientID = orderToBeUpdated.getClientID();
        int quantityU = orderToBeUpdated.getQuantity();
        ProductDAO prDAO = new ProductDAO();
        Product myProduct = prDAO.findById(productID);
        int productStock = myProduct.getStock();
        if (productStock + quantityU - quan >= 0) {
            myProduct.setStock(productStock + quantityU - quan);
            ProductDAO.update(myProduct);
            Orders myOrder = new Orders(idU, clientID, productID, quan);
            OrderDAO.update(myOrder);
        } else {
            JOptionPane.showMessageDialog(null, "Not enough in stock");
        }
    }

    /**
     *  Calls the find by id function
     * @param id
     * @return
     */
    public Orders findById(int id) {
        return orderDAO.findById(id);
    }
}
