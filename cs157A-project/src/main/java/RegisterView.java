/**
 * Created by SteveWang on 11/27/15.
 */

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class RegisterView {
    private CardLayout cardPanels;
    private JPanel panels;
    private JPanel register;
    private JFrame frame;
    private DataSource ds;
    private JTextField firstReg;
    private JTextField lastReg;
    private JTextField userReg;
    private JPasswordField passReg;
    private Connection connection;

    public RegisterView(DataSource ds, JFrame frame, JPanel panels,  CardLayout cardPanels, Connection connection) {
        this.ds = ds;
        this.frame = frame;
        this.panels = panels;
        this.cardPanels = cardPanels;
        this.connection = connection;

        launchRegisterMenu();
    }

    public void launchRegisterMenu() {
        firstReg = new JTextField(10);
        lastReg = new JTextField(10);
        userReg = new JTextField(10);
        passReg = new JPasswordField(10);

        JButton registerButton = new JButton("Register");

        register = new JPanel();

        register.add(new JLabel("First: "));
        register.add(firstReg);
        register.add(new JLabel("Last: "));
        register.add(lastReg);
        register.add(new JLabel("Username: "));
        register.add(userReg);
        register.add(new JLabel("Password: "));
        register.add(passReg);
        register.add(registerButton);

        JButton backButton = new JButton("Back");
        register.add(backButton);

        panels.add(register, "Register");
        cardPanels.show(panels, "Register");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "Home");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
                for (Component C : register.getComponents()) {
                    if (C instanceof JTextField) {
                        ((JTextField) C).setText("");
                    }
                }
                JOptionPane.showMessageDialog(frame,
                        "Account Created!");
                cardPanels.show(panels, "Home");
            }
        });
    }

    public void createAccount() {
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            PreparedStatement stmt = null;
            try {
                stmt = connection.prepareStatement("INSERT INTO Customer(first, last, username, password) " +
                        "VALUES(?, ?, ?, ?);");
                stmt.setString(1, firstReg.getText());
                stmt.setString(2, lastReg.getText());
                stmt.setString(3, userReg.getText());
                stmt.setString(4, new String(passReg.getPassword()));
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}