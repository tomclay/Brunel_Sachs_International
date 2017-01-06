package com.brunel.sachs.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;

/**
 * Created by Tom Clay ESQ.
 */

public class createDb extends Account {

    /**
     * This class creates and enters in account data into the SQL database
     *
     * Only used initially to create the base data
     */

    /**
     * Creates the SQL table using a predetermined schema
     */

    public static void create() {

        Connection c;
        Statement stmt;
        try {
            // Get connection to DB
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            // Build statement
            stmt = c.createStatement();
            String sql = "CREATE TABLE ACCOUNTS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " BALANCE         REAL)";
            // Execute statement
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            transactionLogging.log(Level.SEVERE, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        transactionLogging.log(Level.INFO,"Created table successfully");
    }

    /**
     * Creates initial accounts and balances
     */

    public static void addData() {

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);
            // Build and execute statements indivudally
            stmt = c.createStatement();
            String sql = "INSERT INTO ACCOUNTS (ID,BALANCE) " +
                    "VALUES (1, 100.00 );";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO ACCOUNTS (ID,BALANCE) " +
                    "VALUES (2, 148.00 );";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO ACCOUNTS (ID,BALANCE) " +
                    "VALUES (3, 867.00 );";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO ACCOUNTS (ID,BALANCE) " +
                    "VALUES (4, 372.00 );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            transactionLogging.log(Level.SEVERE, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        transactionLogging.log(Level.INFO,"Records created successfully");
    }
}
