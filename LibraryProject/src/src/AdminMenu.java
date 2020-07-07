/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import net.proteanit.sql.DbUtils;

//Admin GUI
public class AdminMenu {

    private JFrame frame;
    private JButton createDatabaseButton, viewBooksButton, viewUsersButton, viewIssuedBooksButton, addUserButton, addBookButton, issuebookbutton, returnBookButton;

    //constructor, dosnt take arguments
    public AdminMenu() {
        setupResetDatabaseButton();
        setupViewBooksButton();
        setupViewUsersButton();
        setupViewIssuedBooksButton();
        setupAddUserButton();
        setupAddBookButton();
        setupIssueBookButton();
        setupReturnBookButton();
        setupFrame();
    }

    //reset database button with actionlistener
    private void setupResetDatabaseButton() {
        createDatabaseButton = new JButton("Reset Database");
        createDatabaseButton.setBounds(450, 60, 120, 25);
        createDatabaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                DatabaseSQL.createDatabase();
                JOptionPane.showMessageDialog(null, "Database Reset!");

            }
        });

    }

    //view all books button with actionlistener
    private void setupViewBooksButton() {
        viewBooksButton = new JButton("View Books");
        viewBooksButton.setBounds(20, 20, 120, 25);
        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame f = new JFrame("Books Available");
                Connection connection = DatabaseSQL.connectToDatabase();
                String sql = "select * from BOOKS"; //select all books 
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE LIBRARY");
                    stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable bookList = new JTable(); //view data in table format
                    bookList.setModel(DbUtils.resultSetToTableModel(rs));

                    JScrollPane scroll = new JScrollPane(bookList); //scroll bar
                    f.add(scroll);
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

    //view all users button with actionlistener
    private void setupViewUsersButton() {
        viewUsersButton = new JButton("View Users");
        viewUsersButton.setBounds(150, 20, 120, 25);
        viewUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame f = new JFrame("Users List");
                Connection connection = DatabaseSQL.connectToDatabase();
                String sql = "select * from users"; //all users
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE LIBRARY");
                    stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable bookList = new JTable();
                    bookList.setModel(DbUtils.resultSetToTableModel(rs));
                    JScrollPane scroll = new JScrollPane(bookList);
                    f.add(scroll); //add scrollbar
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

    //view issued books actionlistener
    private void setupViewIssuedBooksButton() {
        viewIssuedBooksButton = new JButton("View Issued Books");
        viewIssuedBooksButton.setBounds(280, 20, 160, 25);
        viewIssuedBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Users List");
                Connection connection = DatabaseSQL.connectToDatabase();
                String sql = "select * from issued";//all issued
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE LIBRARY");
                    stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable bookList = new JTable();
                    bookList.setModel(DbUtils.resultSetToTableModel(rs));
                    JScrollPane scroll = new JScrollPane(bookList); //scroll
                    f.add(scroll);
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

    //add new user button with actionlistener
    private void setupAddUserButton() {

        addUserButton = new JButton("Add User"); //creating instance of JButton to add users
        addUserButton.setBounds(20, 60, 120, 25); //set dimensions for button

        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Enter User Details");
                JLabel l1, l2;

                l1 = new JLabel("Username");  //label 1 for username
                l1.setBounds(30, 15, 100, 30);

                l2 = new JLabel("Password");  //label 2 for password
                l2.setBounds(30, 50, 100, 30);

                JTextField userField = new JTextField();
                userField.setBounds(110, 15, 200, 30);

                JPasswordField passwordField = new JPasswordField();
                passwordField.setBounds(110, 50, 200, 30);

                //set radio button for admin
                JRadioButton adminButton = new JRadioButton("Admin");
                adminButton.setBounds(55, 80, 60, 30);

                //set radio button for user
                JRadioButton userButton = new JRadioButton("User");
                userButton.setBounds(130, 80, 60, 30);

                ButtonGroup bg = new ButtonGroup();
                bg.add(userButton);
                bg.add(adminButton);

                JButton button = new JButton("Create");
                button.setBounds(130, 130, 80, 25);
                button.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        String username = userField.getText();
                        String password = new String(passwordField.getPassword());

                        if (username.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Please enter username");

                        } else if (password.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Please enter password");

                        }

                        Boolean admin = false;

                        if (adminButton.isSelected()) {
                            admin = true;
                        }

                        Connection connection = DatabaseSQL.connectToDatabase();

                        try {
                            Statement stmt = connection.createStatement();
                            stmt.executeUpdate("USE LIBRARY");
                            stmt.executeUpdate("INSERT INTO USERS(USERNAME,PASSWORD,ADMIN) VALUES ('" + username + "','" + password + "'," + admin + ")");
                            JOptionPane.showMessageDialog(null, "User added!");
                            f.dispose();

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }

                    }

                });

                f.add(button);
                f.add(userButton);
                f.add(adminButton);
                f.add(l1);
                f.add(l2);
                f.add(userField);
                f.add(passwordField);
                f.setSize(350, 200);//400 width and 500 height  
                f.setLayout(null);//using no layout managers  
                f.setVisible(true);//making the frame visible 
                f.setLocationRelativeTo(null);
            }
        });

    }

    //add books to database button with actionlistener
    private void setupAddBookButton() {
        addBookButton = new JButton("Add Book"); //creating instance of JButton for adding books
        addBookButton.setBounds(150, 60, 120, 25);

        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //set frame wot enter book details
                JFrame f = new JFrame("Enter Book Details");
                // set labels
                JLabel l1, l2, l3;
                l1 = new JLabel("Book Name");
                l1.setBounds(30, 15, 100, 30);

                l2 = new JLabel("Genre");
                l2.setBounds(30, 53, 100, 30);

                l3 = new JLabel("Author");
                l3.setBounds(30, 90, 100, 30);

                //set text field for book name
                JTextField bookNameField = new JTextField();
                bookNameField.setBounds(110, 15, 200, 30);

                //set text field for genre 
                JTextField genreField = new JTextField();
                genreField.setBounds(110, 53, 200, 30);
                //set text field for price
                JTextField authorField = new JTextField();
                authorField.setBounds(110, 90, 200, 30);

                JButton button = new JButton("Submit");
                button.setBounds(130, 130, 80, 25);
                button.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        // assign the book name, genre, price
                        String bname = bookNameField.getText();
                        String genre = genreField.getText();
                        String author = authorField.getText();

                        if (bname.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Please enter bookname correctly ");

                        } else if (genre.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Please enter genre correctly ");

                        } else if (author.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Please enter Author correctly ");

                        } else {

                            Connection connection = DatabaseSQL.connectToDatabase();

                            try {
                                Statement stmt = connection.createStatement();
                                stmt.executeUpdate("USE LIBRARY");
                                String data = "('" + bname + "','" + genre + "','" + author + "')";
                                stmt.executeUpdate("INSERT INTO BOOKS(BNAME,GENRE,AUTHOR) VALUES " + data);
                                JOptionPane.showMessageDialog(null, "Book added!");
                                f.dispose();

                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        }
                    }

                });

                f.add(button);
                f.add(l1);
                f.add(l2);
                f.add(l3);
                f.add(bookNameField);
                f.add(genreField);
                f.add(authorField);
                f.setSize(350, 200);//400 width and 500 height  
                f.setLayout(null);//using no layout managers  
                f.setVisible(true);//making the frame visible 
                f.setLocationRelativeTo(null);

            }
        });

    }

    //issue books button with actionlistener
    private void setupIssueBookButton() {
        issuebookbutton = new JButton("Issue Book"); //creating instance of JButton to issue books
        issuebookbutton.setBounds(450, 20, 120, 25);
        issuebookbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Enter Details");
                JLabel l1, l2, l3, l4;
                l1 = new JLabel("Book ID(BID)");  // Label 1 for Book ID
                l1.setBounds(30, 15, 100, 30);

                l2 = new JLabel("User ID(UID)");  //Label 2 for user ID
                l2.setBounds(30, 53, 100, 30);

                l3 = new JLabel("Period(days)");  //Label 3 for period
                l3.setBounds(30, 90, 100, 30);

                l4 = new JLabel("Issued Date(DD.MM.YYYY)");  //Label 4 for issue date
                l4.setBounds(30, 127, 150, 30);

                JTextField bidField = new JTextField();
                bidField.setBounds(110, 15, 200, 30);

                JTextField uidField = new JTextField();
                uidField.setBounds(110, 53, 200, 30);

                JTextField periodField = new JTextField();
                periodField.setBounds(110, 90, 200, 30);

                JTextField issueField = new JTextField();
                issueField.setBounds(180, 130, 130, 30);

                JButton button = new JButton("Submit");
                button.setBounds(130, 170, 80, 25);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String uid, bid, period;
                        String issuedDate = null;
                        boolean dateAcceptable = false;
                        try {

                            DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
                            Date date = df.parse(issueField.getText());
                            issuedDate = df.format(date);
                            dateAcceptable = true;
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(null, ex + "\nplease enter date correctly");
                        }
                        if (dateAcceptable) {
                            uid = uidField.getText();
                            bid = bidField.getText();
                            period = periodField.getText();

                            if (uid.isBlank() || !uid.matches("[0-9]+")) {
                                JOptionPane.showMessageDialog(null, "Please enter UID correctly ");

                            } else if (bid.isBlank() || !bid.matches("[0-9]+")) {
                                JOptionPane.showMessageDialog(null, "Please enter BID correctly");

                            } else if (period.isBlank() || !period.matches("[0-9]+")) {
                                JOptionPane.showMessageDialog(null, "Please enter period correclty");

                            } else {

                                int periodInt = Integer.parseInt(period);

                                Connection connection = DatabaseSQL.connectToDatabase();

                                try {
                                    Statement stmt = connection.createStatement();
                                    stmt.executeUpdate("USE LIBRARY");
                                    stmt.executeUpdate("INSERT INTO ISSUED(UID,BID,ISSUED_DATE,PERIOD) VALUES ('" + uid + "','" + bid + "','" + issuedDate + "'," + periodInt + ")");
                                    JOptionPane.showMessageDialog(null, "Book Issued!");
                                    f.dispose();

                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                }
                            }
                        }
                    }
                });

                f.add(button);
                f.add(l1);
                f.add(l2);
                f.add(l3);
                f.add(l4);
                f.add(uidField);
                f.add(bidField);
                f.add(periodField);
                f.add(issueField);
                f.setSize(350, 250);
                f.setLayout(null);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
            }
        }
        );
    }

    //return books button with actionlistener
    private void setupReturnBookButton() {
        returnBookButton = new JButton("Return Book");
        returnBookButton.setBounds(280, 60, 160, 25);
        returnBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame f = new JFrame("Enter Details");
                JLabel l1, l2;

                l1 = new JLabel("Issue ID(IID)");  //Label 1 for Issue ID
                l1.setBounds(30, 15, 100, 30);

                l2 = new JLabel("Return Date(DD.MM.YYYY)");
                l2.setBounds(30, 50, 150, 30);

                JTextField iidField = new JTextField();
                iidField.setBounds(110, 15, 200, 30);

                JTextField returnDayField = new JTextField();
                returnDayField.setBounds(180, 50, 130, 30);

                JButton button = new JButton("Return");
                button.setBounds(130, 170, 80, 25);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String rDate = null;
                        String iid = null;
                        boolean dateAcceptable = false;

                        try {
                            DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
                            Date date = format.parse(returnDayField.getText());
                            rDate = format.format(date);
                            dateAcceptable = true;
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(null, ex + "\nplease enter date correctly");
                        }

                        if (dateAcceptable) {
                            iid = iidField.getText();
                            if (!iid.matches("[0-9]+")) {
                                JOptionPane.showMessageDialog(null, "Please enter IID correctly");

                            } else {

                                Connection connection = DatabaseSQL.connectToDatabase();

                                try {
                                    Statement stmt = connection.createStatement();
                                    stmt.executeUpdate("USE LIBRARY");
                                    String date1 = null;
                                    String date2 = rDate;

                                    //select issue date
                                    ResultSet rs = stmt.executeQuery("SELECT ISSUED_DATE FROM ISSUED WHERE IID=" + iid);
                                    while (rs.next()) {
                                        date1 = rs.getString(1);

                                    }

                                    int daysLoaned = 0;

                                    try {
                                        Date loanDate = new SimpleDateFormat("dd.mm.yyyy").parse(date1);
                                        Date returnDate = new SimpleDateFormat("dd.mm.yyyy").parse(date2);
                                        //subtract the dates and store in diff
                                        long diff = returnDate.getTime() - loanDate.getTime();
                                        //Convert diff from milliseconds to days
                                        daysLoaned = (int) (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

                                    } catch (ParseException ex) {
                                        JOptionPane.showMessageDialog(null, ex);
                                    }

                                    //update return date
                                    stmt.executeUpdate("UPDATE ISSUED SET RETURN_DATE='" + rDate + "' WHERE IID=" + iid);
                                    f.dispose();

                                    Connection connection1 = DatabaseSQL.connectToDatabase();
                                    Statement stmt1 = connection1.createStatement();
                                    stmt1.executeUpdate("USE LIBRARY");
                                    ResultSet rs1 = stmt1.executeQuery("SELECT PERIOD FROM ISSUED WHERE IID=" + iid);

                                    String loanPeriodS = null;
                                    while (rs1.next()) {
                                        loanPeriodS = rs1.getString(1);

                                    }
                                    int loanPeriod = Integer.parseInt(loanPeriodS);
                                    if (daysLoaned > loanPeriod) { //If number of days loaned is more than the period then calculcate fine
                                        double fine = (daysLoaned - loanPeriod) * 0.30; //fine for every day after the period is 30 cents.
                                        stmt1.executeUpdate("UPDATE ISSUED SET FINE=" + fine + " WHERE IID=" + iid);
                                        String fineString = ("Fine: " + fine + " €");
                                        JOptionPane.showMessageDialog(null, fineString);

                                    }

                                    JOptionPane.showMessageDialog(null, "Book Returned!");

                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                }
                            }
                        }
                    }
                });
                f.add(button);
                f.add(l1);
                f.add(l2);
                f.add(iidField);
                f.add(returnDayField);
                f.setSize(350, 250);
                f.setLayout(null);
                f.setVisible(true);
                f.setLocationRelativeTo(null);

            }
        });
    }

    //add all buttons to frame
    private void setupFrame() {
        frame = new JFrame("Admin Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
        frame.add(createDatabaseButton);
        frame.add(viewBooksButton);
        frame.add(viewUsersButton);
        frame.add(viewIssuedBooksButton);
        frame.add(addUserButton);
        frame.add(addBookButton);
        frame.add(issuebookbutton);
        frame.add(returnBookButton);
        frame.setSize(600, 200);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
