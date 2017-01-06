package com.brunel.sachs.Server;

import java.sql.*;

/**
 * Created by Tom Clay ESQ. on 08/12/2016.
 */
public class createDb extends Account {

    public static void create() {

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");

            stmt = c.createStatement();
            String sql = "CREATE TABLE ACCOUNTS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " BALANCE         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Created database successfully");

    }

    public static void addData() {

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mainframe.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");

    }
}
