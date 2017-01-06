package com.brunel.sachs.Server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by tomclay on 08/12/2016.
 */

public class Database extends Account {

    // Get the account from the database
    public synchronized static float get_bal(int accID) {

        Connection c;
        Statement stmt;
        float balance = 00;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACCOUNTS WHERE ID = " + accID + ";");
            while ( rs.next() ) {
                balance = rs.getFloat("balance");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return balance;
    }


    public synchronized static void update_bal(int accID, float amount){
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE ACCOUNTS SET BALANCE =" + amount + " WHERE ID =" + accID + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }
}
