package presentation.validators;

import model.Product;

/**
 * Class ProductValidator contains the validators for the client data
 */
public class ProductValidator {

    public boolean validateID(int ID){

        if(ID < 0)
            return false;

        return true;
    }

    /**
     * Checks the name of the product
     * @param name
     * @return
     */

    public boolean validateName(String name){

        if(name == "")
            return false;
        else if(!name.matches("[ a-zA-Z]+"))
            return false;

        return true;
    }

    /**
     * Checks the price of the product
     * @param price
     * @return
     */

    public boolean validatePrice( int price){

        if(price < 0)
            return false;

        return true;
    }

    /**
     * Checks the stock of the product
     * @param stock
     * @return
     */
    public boolean validateStock(int stock){

        if(stock < 0)
            return false;

        return true;
    }

    /**
     * Checks if the product given respects all the conditions
     * @param product
     * @return
     */
    public boolean validateProduct(Product product){

        if(!validateID(product.getId()))
            return false;
        else if(!validateName(product.getName()))
            return false;
        else if(!validatePrice(product.getPrice()))
            return false;
        else if(!validateStock(product.getStock()))
            return false;

        return true;
    }
}
