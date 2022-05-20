package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    Container container = getContentPane();
    private JButton clientButton = new JButton("Clients");
    private JButton productButton = new JButton("Products");
    private JButton ordersButton = new JButton("Orders");
    private JButton closeButton = new JButton("Close");


    public View() {
        setLayoutManag();
        setLocAndSize();
        addComponent();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 500, 400);
    }


    public void setLayoutManag() {
        container.setLayout(null);
    }

    public void setLocAndSize() {

        clientButton.setBounds(10,70,200,50);

        productButton.setBounds(10,220,200,50);

        ordersButton.setBounds(270,70,200,50);

        closeButton.setBounds(270, 220,200,50);


    }

    public void addComponent() {
        container.add(clientButton);
        container.add(productButton);
        container.add(ordersButton);
        container.add(closeButton);
    }

    public void clientView(ActionListener actionListener)
    {
        clientButton.addActionListener(actionListener);
    }

    public void productView(ActionListener a)
    {
        productButton.addActionListener(a);
    }

    public void orderView(ActionListener a)
    {
        ordersButton.addActionListener(a);
    }

    public void closeView(ActionListener a)
    {
        closeButton.addActionListener(a);
    }

}
