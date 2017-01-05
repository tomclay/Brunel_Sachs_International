package com.brunel.sachs.Server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by tomclay on 08/12/2016.
 */

public class Database extends Accounts {

    public static float accBalance;
    public static String accId;

    public Database(String account_id) {
        this.accId = account_id;
    }

    // Get the account from the database
    public synchronized static float get_bal() {

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACCOUNTS WHERE ID = " + accId + ";");
            while ( rs.next() ) {
                accBalance = rs.getFloat("balance");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return accBalance;
    }

    public static float add(float amount){
        float newBalance = accBalance + amount;
        accBalance = newBalance;
        update_bal(String.valueOf(newBalance));
        return accBalance;
    }

    public static float subtract(float amount){
        float newBalance = accBalance - amount;
        accBalance = newBalance;
        update_bal(String.valueOf(newBalance));
        return accBalance;
    }


    public synchronized static void update_bal(String amount){
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE ACCOUNTS SET BALANCE =" + amount + " WHERE ID =" + accId + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

    public synchronized static float wire(String dest_account_ID, float amount){
        // assuming global source account

        float dest_account_bal = 0, dest_account_new_bal = 0;

        // frist, subtract the to-be-moved amount from the source account
        subtract(amount);

        // Can't use the standard add method as I am going to use another account ID
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACCOUNTS WHERE ID = " + dest_account_ID + ";");
            while ( rs.next() ) {
                dest_account_bal = rs.getFloat("balance");
            }

            rs.close();
            stmt.close();


            dest_account_new_bal = dest_account_bal + amount;

            stmt = c.createStatement();
            String sql = "UPDATE ACCOUNTS SET BALANCE =" + dest_account_new_bal + " WHERE ID =" + dest_account_ID + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return dest_account_new_bal;

    }
}
