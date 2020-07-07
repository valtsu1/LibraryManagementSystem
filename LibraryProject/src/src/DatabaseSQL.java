package src;

import java.sql.*;

//Static class for connecting to database
public class DatabaseSQL {

    private static final String databaseName = "library";

    public static void createDatabase() {

        try {
            Connection connection = connectToDatabase();
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next()) {
                String dbName = resultSet.getString(1);
                if (dbName.equals(databaseName)) {
                    deleteDatabase();       //deletes old databasr
                }
            }

            Statement stmt = connection.createStatement();
            
            //create new database using statement executeUpdate for commands
            stmt.executeUpdate("CREATE DATABASE LIBRARY");
            stmt.executeUpdate("USE LIBRARY"); 
            
            //Create Users Table
            stmt.executeUpdate("CREATE TABLE USERS(UID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, USERNAME VARCHAR(30), PASSWORD VARCHAR(30), ADMIN BOOLEAN)");
            
            //Insert into users table
            stmt.executeUpdate("INSERT INTO USERS(USERNAME, PASSWORD, ADMIN) VALUES('admin','admin',TRUE)");
            
            //Create Books table
            stmt.executeUpdate("CREATE TABLE BOOKS(BID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, BNAME VARCHAR(50), GENRE VARCHAR(20), AUTHOR VARCHAR(30))");
            
            //Create Issued Table
            stmt.executeUpdate("CREATE TABLE ISSUED(IID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, UID INT, BID INT, ISSUED_DATE VARCHAR(20), RETURN_DATE VARCHAR(20), PERIOD INT, FINE INT)");
            
            //Insert into books table
            stmt.executeUpdate("INSERT INTO BOOKS(BNAME, GENRE, AUTHOR) VALUES ('Shining', 'Horror', 'King'),  ('Catcher in the Rye', 'Fiction', 'Salinger'), ('Republic','Philosophy','Plato')");

            System.out.print("DATABASE CREATED");
            
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    //most used static method, returns connection
    public static Connection connectToDatabase() {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user=root&password=root");
            System.out.println("Connected to MySQL");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        return con;
    }

    //used to check if database exists
    public static boolean checkIfDatabaseExists() {

        boolean exists = false;

        try {

            Connection connection = connectToDatabase();
            ResultSet resultSet = connection.getMetaData().getCatalogs();

            while (resultSet.next()) {
                String dbName = resultSet.getString(1);
                if (dbName.equals(databaseName)) {
                    exists = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return exists;

    }

    private static void deleteDatabase() {
        try {
            Connection con = connectToDatabase();
            Statement stmt = con.createStatement();
            String sql = "DROP DATABASE " + databaseName;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }


}
