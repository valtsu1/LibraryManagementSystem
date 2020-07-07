/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

//Users gui class
public class UserMenu {

    private JFrame frame;
    private JButton viewAllBooksButton, myLoanedBooksButton;

    //contructor, requires user id
    public UserMenu(String UID) {
        setupViewBooksButton();
        setupMyLoanedBooksButton(UID);
        setupFrame();

    }

    //setupframes
    private void setupFrame() {
        frame = new JFrame("User Menus");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(viewAllBooksButton); 
        frame.add(myLoanedBooksButton); 
        frame.setSize(300, 100);  
        frame.setLayout(null); 
        frame.setVisible(true); 
        frame.setLocationRelativeTo(null);

    }

    //view users loaned books, takes user id as parameter, actionlistener
    private void setupMyLoanedBooksButton(String UID) {
        myLoanedBooksButton = new JButton("My Books");
        myLoanedBooksButton.setBounds(150, 20, 120, 25);
        myLoanedBooksButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("My Books"); 
                int UID_int = Integer.parseInt(UID); //User id to integer

                Connection connection = DatabaseSQL.connectToDatabase(); 
                
                String sql = "select distinct issued.*,books.bname,books.genre,books.author from issued,books " + "where ((issued.uid=" + UID_int + ") and (books.bid in (select bid from issued where issued.uid=" + UID_int + "))) group by iid";
                
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE LIBRARY");
                    stmt = connection.createStatement();

                    ResultSet rs = stmt.executeQuery(sql);
                    JTable bookList = new JTable(); //store data in table format
                    bookList.setModel(DbUtils.resultSetToTableModel(rs));
                    
                    //enable scroll bar
                    JScrollPane scrollPane = new JScrollPane(bookList);
                    f.add(scrollPane); //add scroll bar
                    
                    f.setSize(800, 400);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }

            }
        }
        );
    }
    
    //view all books, actionlistener
    private void setupViewBooksButton() {
        viewAllBooksButton = new JButton("View Books");
        viewAllBooksButton.setBounds(20, 20, 120, 25);
        viewAllBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame f = new JFrame("Books Available");
                Connection connection = DatabaseSQL.connectToDatabase();
                String sql = "select * from BOOKS";
                
                try {
                    Statement stmt = connection.createStatement(); 
                    stmt.executeUpdate("USE LIBRARY");
                    stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable bookList = new JTable(); 
                    bookList.setModel(DbUtils.resultSetToTableModel(rs));

                    JScrollPane scroll = new JScrollPane(bookList); 

                    f.add(scroll); //add scroll bar
                    f.setSize(800, 400); 
                    f.setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }

            }
        }
        );

    }

}
