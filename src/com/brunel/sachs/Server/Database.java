package com.brunel.sachs.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

/**
 * Created by Tom Clay ESQ.
 */

public class Database extends Account {

    /**
     * This class directly accesses the database to get and set account balances.
     * To protect from concurrency issues, all methods are synchronised.
     */

    /**
     * A method to get the balance of an account id
     *
     * @param i The index of the account
     * @return The balance of account i
     */

    public synchronized static float get_bal(int i) {

        Connection c;
        Statement stmt;
        float balance = 00;
        try {
            // Get connection
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            // Insert ID from user input
            ResultSet rs = stmt.executeQuery("SELECT * FROM ACCOUNTS WHERE ID = " + i + ";");
            while (rs.next()) {
                balance = rs.getFloat("balance");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            transactionLogging.log(Level.SEVERE, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return balance;
    }

    /**
     * A method to uppdate the balance of a specified account
     *
     * @param i The index of the account
     * @param amount The amount to change the balance
     */

    public synchronized static void update_bal(int i, float amount) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE ACCOUNTS SET BALANCE =" + amount + " WHERE ID =" + i + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            transactionLogging.log(Level.SEVERE, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
