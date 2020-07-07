/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

//LoginGui, leads to AdminMenu or UserMenu depending of user
public class LoginGUI implements ActionListener {

    private final JFrame frame;
    private final JLabel usernameLabel, passwordLabel;

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    private final JButton loginButton;

    private String username;
    private String password;

    //constructor
    public LoginGUI() {

        frame = new JFrame("Login");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        frameSetup();

    }

    //frame setup
    private void frameSetup() {
        usernameLabel.setBounds(30, 15, 100, 30);
        passwordLabel.setBounds(30, 50, 100, 30);
        usernameField.setBounds(110, 15, 200, 30);
        passwordField.setBounds(110, 50, 200, 30);
        loginButton.setBounds(130, 90, 80, 25);

        frame.add(loginButton);
        frame.add(usernameField);
        frame.add(usernameLabel);
        frame.add(passwordField);
        frame.add(passwordLabel);

        frame.setSize(400, 200);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        loginButton.addActionListener(this);

    }

    @Override
    //this class has only one button that needs actionlistening
    public void actionPerformed(ActionEvent e
    ) {
        username = usernameField.getText();
        password = new String(passwordField.getPassword());
        if (username.isBlank()) {                     //get correct user input
            JOptionPane.showMessageDialog(null, "Please enter username");
        } else if (password.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please enter password");
        } else {
            Connection connection = DatabaseSQL.connectToDatabase();
            try {
                Statement stmt = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                );
                stmt.executeUpdate("USE LIBRARY");
                String st = ("SELECT * FROM USERS WHERE USERNAME='" + username + "' AND PASSWORD='" + password + "'"); //get user 
                ResultSet rs = stmt.executeQuery(st);
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(null, "Wrong Username or Password!");

                } else {
                    frame.dispose();
                    rs.beforeFirst();
                    while (rs.next()) {
                        String admin = rs.getString("ADMIN");
                        String UID = rs.getString("UID"); //Get user ID of the user
                        if (admin.equals("1")) {
                            new AdminMenu();
                        } else {
                            new UserMenu(UID);
                        }
                    }
                }
            } catch (HeadlessException | SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

        }
    }
}
