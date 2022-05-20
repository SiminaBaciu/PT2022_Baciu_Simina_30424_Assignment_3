package presentation;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OrderView extends JFrame {

    Container container = getContentPane();

    private JLabel idLabel = new JLabel("ID: ");
    private JTextField idField = new JTextField();
    private JLabel idClientLabel = new JLabel("Client ID: ");
    private JLabel idProductLabel = new JLabel("Product ID: ");
    private JLabel quantityLabel = new JLabel("Quantity: ");
    private JTextField quantityField = new JTextField();
    private JComboBox<Integer> cBox1 = new JComboBox<>();
    private JComboBox<Integer> cBox2 = new JComboBox<>();
    private JScrollPane scrollPane = new JScrollPane();
    private JButton back = new JButton("Back");
    private JButton insert = new JButton("Insert");
    private JButton delete = new JButton("Delete");
    private JButton update = new JButton("Update");
    private JButton bill = new JButton("Generate Bill");


    public OrderView(View parentInterface) {

        setLayoutManag();
        setLocAndSize(parentInterface);
        addComponent();
        this.setTitle("Orders");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);
    }

    public void setLayoutManag() {
        container.setLayout(null);
    }

    public void setLocAndSize(View parentInterface) {

        idLabel.setBounds(50, 30, 100, 30);
        idField.setBounds(80, 30, 75, 30);

        idClientLabel.setBounds(170, 30, 200, 30);
        cBox1.setBounds(240, 30, 125, 30);

        idProductLabel.setBounds(370, 30, 100, 30);
        cBox2.setBounds(440, 30, 125, 30);

        quantityLabel.setBounds(580, 30, 100, 30);
        quantityField.setBounds(630, 30, 125, 30);

        insert.setBounds(200, 520, 100, 50);
        delete.setBounds(330, 520, 100, 50);
        update.setBounds(460, 520, 100, 50);
        back.setBounds(590, 520, 100, 50);
        back.addActionListener(e -> {
            setVisible(false);
            parentInterface.setVisible(true);

        });

        scrollPane.setBounds(140, 90, 600, 400);

        bill.setBounds(355, 590, 180, 50);

    }

    public void addComponent() {
        container.add(back);
        container.add(delete);
        container.add(insert);
        container.add(update);
        container.add(scrollPane);
        container.add(cBox1);
        container.add(cBox2);
        container.add(bill);
        container.add(idClientLabel);
        container.add(idField);
        container.add(idLabel);
        container.add(idProductLabel);
        container.add(quantityField);
        container.add(quantityLabel);

    }

    public String getIdField() {
        return idField.getText();
    }

    public int getClientID() {
        return (Integer) cBox1.getSelectedItem();
    }

    public int getProductID() {
        return (Integer) cBox2.getSelectedItem();
    }

    public String getQuantity() {
        return quantityField.getText();
    }

    public JComboBox getComboBox(){
        return cBox1;
    }

    public JComboBox getComboBox2(){
        return cBox2;
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }


    public void insertListener(ActionListener a)
    {
        insert.addActionListener(a);
    }

    public void deleteListener(ActionListener a)
    {
        delete.addActionListener(a);
    }

    public void updateListener(ActionListener a)
    {
        update.addActionListener(a);
    }

    public void generateListener(ActionListener a)
    {
        bill.addActionListener(a);
    }




}
