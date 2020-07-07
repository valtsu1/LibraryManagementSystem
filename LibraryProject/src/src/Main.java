/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;


import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        //Makes the GUI loook  like  native Windows app instea of Java.
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace(System.out);
        }
        
        if (!DatabaseSQL.checkIfDatabaseExists()) {
            DatabaseSQL.createDatabase();
        }
        
        new LoginGUI();
    }

}
