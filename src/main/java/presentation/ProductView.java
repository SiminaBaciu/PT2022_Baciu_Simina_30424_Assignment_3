package presentation;


import java.awt.*;

import java.awt.event.ActionListener;


import javax.swing.*;


public class ProductView extends JFrame {

    Container container = getContentPane();

    private JLabel idLabel = new JLabel("ID: ");
    private JTextField idField = new JTextField();
    private JLabel nameLabel = new JLabel("Name: ");
    private JTextField nameField = new JTextField();
    private JLabel priceLabel = new JLabel("Price: ");
    private JTextField priceField = new JTextField();
    private JLabel stockLabel = new JLabel("Stock: ");
    private JTextField stockField = new JTextField();
    private JScrollPane scrollPane = new JScrollPane();

    private JButton back = new JButton("Back");
    private JButton insert = new JButton("Insert");
    private JButton delete = new JButton("Delete");
    private JButton update = new JButton("Update");


    public ProductView(View parentInterface) {
        ProductView pInt = this;
        setLayoutManag();
        setLocAndSize(parentInterface);
        addComponent();
        this.setTitle("Products");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);
        this.getContentPane().setLayout(null);
    }

    public void setLayoutManag() {
        container.setLayout(null);
    }

    public void setLocAndSize(View parentInterface) {

        idLabel.setBounds(50, 30, 100, 30);
        idField.setBounds(80, 30, 75, 30);

        nameLabel.setBounds(170, 30, 200, 30);
        nameField.setBounds(240, 30, 125, 30);

        priceLabel.setBounds(370, 30, 100, 30);
        priceField.setBounds(440, 30, 125, 30);

        stockLabel.setBounds(580, 30, 100, 30);
        stockField.setBounds(630, 30, 125, 30);


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

    public void addComponent() {
        container.add(back);
        container.add(insert);
        container.add(delete);
        container.add(update);
        container.add(idField);
        container.add(idLabel);
        container.add(nameField);
        container.add(nameLabel);
        container.add(priceField);
        container.add(priceLabel);
        container.add(stockField);
        container.add(stockLabel);
        container.add(scrollPane);

    }


    public String getIdField() {
        return idField.getText();
    }

    public String getNameField() {
        return nameField.getText();
    }

    public String getPriceField() {
        return priceField.getText();
    }

    public String getStockField() {
        return stockField.getText();
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
