package model;

/**
 * Class Product contains the information about the products of this catering company
 */

public class Product {


    private int id;
    private String name;
    private int price;
    private int stock;

    public Product() {

    }
    /**
     * Constructor of the Product Class
     */
    public Product(int productId, String name, int price, int stock) {
        this.id = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Gets the ID of the product
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the product
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the product
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the product
     */

    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the product
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the stock of the product
     */

    public int getStock() {
        return stock;
    }
    /**
     * Sets the stock of the product
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns the products as strings
     */
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
