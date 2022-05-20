package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * Class ClientView creates the view for the clients
 */
public class ClientView extends JFrame {

    Container container = getContentPane();

    private JLabel idLabel = new JLabel("ID: ");
    private JTextField idField = new JTextField();
    private JLabel firstNameLabel = new JLabel("First Name: ");
    private JTextField firstNameField = new JTextField();
    private JLabel lastNameLabel = new JLabel("Last Name: ");
    private JTextField lastNameField = new JTextField();
    private JLabel emailLabel = new JLabel("Email: ");
    private JTextField emailField = new JTextField();
    private JButton back = new JButton("Back");
    private JButton insert = new JButton("Insert");
    private JButton delete = new JButton("Delete");
    private JButton update = new JButton("Update");
    private JScrollPane scrollPane = new JScrollPane();

    /**
     * Constructor of the clientView class
     * @param parentInterface
     */
    public ClientView(View parentInterface) {

        setLayoutManag();
        setLocAndSize(parentInterface);
        addComponent();
        this.setTitle("Clients");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);
    }

    /**
     * Sets the layout manager
     */
    public void setLayoutManag() {
        container.setLayout(null);
    }

    /**
     * Sets the location and sizes for the view parts
     * @param parentInterface
     */
    public void setLocAndSize(View parentInterface) {
        idLabel.setBounds(50, 30, 100, 30);
        idField.setBounds(80, 30, 75, 30);

        firstNameLabel.setBounds(170, 30, 200, 30);
        firstNameField.setBounds(240, 30, 125, 30);

        lastNameLabel.setBounds(370, 30, 100, 30);
        lastNameField.setBounds(440, 30, 125, 30);

        emailLabel.setBounds(580, 30, 100, 30);
        emailField.setBounds(630, 30, 125, 30);

        insert.setBounds(200, 520, 100, 50);
        delete.setBounds(330, 520, 100, 50);
        update.setBounds(460, 520, 100, 50);
        back.setBounds(590, 520, 100, 50);
        back.addActionListener(e -> {
            setVisible(false);
            parentInterface.setVisible(true);

        });

        scrollPane.setBounds(140, 90, 600, 400);
    }

    /**
     * Adds all view parts into the container
     */
    public void addComponent() {
        container.add(idField);
        container.add(idLabel);
        container.add(firstNameField);
        container.add(lastNameField);
        container.add(firstNameLabel);
        container.add(lastNameLabel);
        container.add(emailField);
        container.add(emailLabel);
        container.add(delete);
        container.add(update);
        container.add(insert);
        container.add(back);
        container.add(scrollPane);
    }

    public String getIdField() {
        return idField.getText();
    }

    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public String getLastNameField() {
        return lastNameField.getText();
    }

    public String getEmailField() {
        return emailField.getText();
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public void insertListener(ActionListener a) {
        insert.addActionListener(a);
    }

    public void deleteListener(ActionListener a) {
        delete.addActionListener(a);
    }

    public void updateListener(ActionListener a) {
        update.addActionListener(a);
    }

}
