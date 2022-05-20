package presentation.validators;

import model.Client;

/**
 * Class ClientValidator contains the validators for the client data
 */
public class ClientValidator {

    /**
     * validates the ids of the clients
     * @param ID
     * @return
     */
    public boolean validateID(int ID) {

        if (ID < 0)
            return false;

        return true;
    }

    /**
     * validates the name of the clients
     * @param name
     * @return
     */

    public boolean validateName(String name) {

        if (name == "")
            return false;
        else if (name.matches("[ a-zA-Z]+") == false)
            return false;

        return true;
    }

    /**
     * validates the emails of the clients
     * @param email
     * @return
     */

    public boolean validateEmail(String email) {
        if (email == "")
            return false;
        else if (!email.contains("@") || !email.contains("."))
            return false;
        return true;
    }

    /**
     * Checks if the client given respects all the conditions
     * @param client
     * @return
     */

    public boolean validateClient(Client client) {

        if (!validateID(client.getID()))
            return false;
        else if (!validateName(client.getFirstName()))
            return false;
        else if (!validateName(client.getLastName()))
            return false;
        else if (!validateEmail(client.getEmail()))
            return false;

        return true;

    }


}
