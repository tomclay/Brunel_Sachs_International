package com.brunel.sachs.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Tom Clay ESQ. on 12/12/2016.
 */

public class Thread extends java.lang.Thread {

    private Socket serverSocket = null;
    private static Account account;
    private static String localServerThreadName;
    private static PrintWriter out;
    //private static BufferedReader in;
    private static Scanner in;

    //Setup the thread
    public Thread(Socket actionSocket, String ServerThreadName, Account accountInstance) {
        this.serverSocket = actionSocket;
        account = accountInstance;
        localServerThreadName = ServerThreadName;
    }


    public void run() {
        try {

            System.out.println(localServerThreadName + " initialising.");
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            in = new Scanner(serverSocket.getInputStream());
            System.out.println(localServerThreadName + " initialised.");
            String inputLine;


//            try {
//                account.acquireLock();
//                account.start();
//                account.choices();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

//            out.close();
//            in.close();

//            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
