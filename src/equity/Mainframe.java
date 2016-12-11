package equity;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by tomclay on 08/12/2016.
 */

public class Mainframe extends Account {

    public static float accBalance;
    public static String accId;

    public Mainframe(String ID_input){
        accId = ID_input;
    }


    // Get the account from the database
    public static float acc_lookup() {

        Connection c = null;
        Statement stmt = null;
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
        update_bal(String.valueOf(newBalance));
        return acc_lookup();
    }

    public static float subtract(float amount){
        float newBalance = accBalance - amount;
        update_bal(String.valueOf(newBalance));
        return acc_lookup();
    }


    public static void update_bal(String amount){
        Connection c = null;
        Statement stmt = null;
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
}
