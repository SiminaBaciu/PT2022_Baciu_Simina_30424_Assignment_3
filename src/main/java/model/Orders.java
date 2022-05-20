package model;

/**
 * Class Orders contains the information about the orders made in the catering company
 */

public class Orders {

    private int id;
    private int clientID;
    private int productID;
    private int quantity;

    public Orders(){

    }

    /**
     * Constructor of the Orders Class
     */

    public Orders(int orderID, int clientID, int productID, int quantity) {
        this.id = orderID;
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    /**
     * Gets the id of the order
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the id of the client that made the order
     */

    public int getClientID() {
        return clientID;
    }
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * Gets the id of the product that was ordered
     */

    public int getProductID() {
        return productID;
    }


    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * Gets the quantity of the product bought
     */

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the orders as strings
     */

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + id +
                ", clientID=" + clientID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                '}';
    }

}
