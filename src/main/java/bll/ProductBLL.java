package bll;

import dao.ProductDAO;
import model.Product;
import presentation.validators.ProductValidator;

import javax.swing.*;
import java.util.NoSuchElementException;

/**
 * Class ProductBLL encapsulate the application logic
 */
public class ProductBLL {

    ProductDAO productDAO;
    ProductValidator productValidator;

    public ProductBLL() {
        productDAO = new ProductDAO();
        productValidator = new ProductValidator();
    }
    /**
     * Calls for the creation of the product table
     * @return
     */
    public JTable createTable(){
        return productDAO.createTable();
    }

    public Product searchByID(int ID){

        Product pr = productDAO.findById(ID);
        if (pr == null) {
            throw new NoSuchElementException("The product with id =" + ID + " was not found!");
        }
        return pr;
    }

    /**
     * Calls the insert function after verifying the product
     * @param product
     * @return
     */
    public int insertNewProduct(Product product)  {
        int productID = 0;
        try {
            productID = product.getId();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ProductValidator prodValid = new ProductValidator();
        Product newProduct = product;

        if (prodValid.validateProduct(newProduct)) {
            return ProductDAO.insert(newProduct);
        } else
            JOptionPane.showMessageDialog(null, "Bad input!");

        return productID;
    }

    /**
     * Calls the delete function after verifying the product
     * @param product
     * @return
     */
    public int delete(Product product) {
        int productID = 0;
        try {
            productID = product.getId();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Product prodDelete = productDAO.findById(productID);
        if (prodDelete != null) {
            return ProductDAO.delete(prodDelete);
        } else
            JOptionPane.showMessageDialog(null, " Error deleting product, product does not exist. ");
        return productID;
    }

    /**
     * Calls the update function after verifying the product
     * @param product
     */
    public void update(Product product) {
        int id = 0;
        int price = 0;
        int stock = 0;
        try {
            id = product.getId();
            price = product.getPrice();
            stock = product.getStock();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        Product prUp = product;
        if (productValidator.validateProduct(product)) {
            Product newP = productDAO.findById(id);
            newP.setName(product.getName());
            newP.setStock(stock);
            newP.setPrice(price);
            ProductDAO.update(newP);
        }
    }
}
