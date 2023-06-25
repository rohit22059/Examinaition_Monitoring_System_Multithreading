/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examinationmonitoringsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Rohit Kesarwani
 */
public class DatabaseHelper {

    Connection c = null;
    Statement s;

    public Statement connect() {
        try {
            
         Class.forName("com.mysql.jdbc.Driver");
          c=DriverManager.getConnection("jdbc:mysql://localhost/ExaminationMonitoring?autoReconnect=true&useSSL=true", "root", "root");
            s = c.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null,"database connection error","error",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
            //JOptionPane.showMessageDialog(null,ce.getException(),"error",JOptionPane.ERROR_MESSAGE);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return s;
    }

    public void commit() {
        try {
            c.commit();
        } catch (Exception e) {
        }
    }

    public void disconnect() {
        try {
            c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "class not disconnect", "error", JOptionPane.ERROR_MESSAGE);

        }
    }
}
