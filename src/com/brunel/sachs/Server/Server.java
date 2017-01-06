package com.brunel.sachs.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;

/**
 * Created by Tom Clay ESQ.
 */

public class Server {

    /**
     * A class to create the server socket connections, create the shared object Accounts,
     * to listen out for clients, and start a thread when a client accesses
     *
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {

        // Setup server socket and port
        ServerSocket MainframeServerSocket = null;
        boolean listening = true;
        String MainframeName = "BrunelMainframe";
        int portNumber = 4545;


        // Creating the shared object Account
        Account sharedAccount = new Account();
        try {
            // Try to create the server socket
            MainframeServerSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            transactionLogging.log(Level.SEVERE, "Could not start " + MainframeName + " specified port.");
            System.exit(-1);
        }
        System.out.println(MainframeName + " started");
        // Create an instance of Transaction for each client
        while (listening) {
            new Transaction(MainframeServerSocket.accept(), sharedAccount, "ServerThread1").start();
            new Transaction(MainframeServerSocket.accept(), sharedAccount, "ServerThread2").start();
            new Transaction(MainframeServerSocket.accept(), sharedAccount, "ServerThread3").start();
            new Transaction(MainframeServerSocket.accept(), sharedAccount, "ServerThread4").start();
            System.out.println("New " + MainframeName + " thread started.");
        }
        // Close when finished
        MainframeServerSocket.close();
    }
}
