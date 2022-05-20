package model;

/**
 * Class Client contains the information about the clients of this catering company
 */
public class Client {

    private int ID;
    private String firstName;
    private String lastName;
    private String email;

    public Client() {

    }
    /**
     * Constructor of the Client Class
     */
    public Client(int ID, String firstName, String lastName, String email) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Get the ID of the clients
     */

    public int getID() {
        return ID;
    }

    /**
     * Set the ID of the clients
     */

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Get the first name of the clients
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the clients
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the clients
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Set the first name of the clients
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the email of the clients
     */

    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the clients
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the clients as strings
     */

    @Override
    public String toString() {
        return "Client{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email =" + email +
                '}';
    }
}
