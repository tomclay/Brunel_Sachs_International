package com.brunel.sachs.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Tom Clay ESQ. on 12/12/2016.
 */

public class Thread extends java.lang.Thread {

    private Socket serverSocket = null;
    private static Accounts accounts;
    private static String localServerThreadName;
    private static PrintWriter out;
    //private static BufferedReader in;
    private static Scanner in;

    //Setup the thread
    public Thread(Socket actionSocket, String ServerThreadName, Accounts accountInstance) {
        this.serverSocket = actionSocket;
        accounts = accountInstance;
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
//                accounts.acquireLock();
//                accounts.start();
//                accounts.choices();
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

    public static void sendMessage(String message){
            System.out.println("Outgoing: " + message);
            out.println(message);
            out.write('\n');
            out.flush();
    }

    public static LinkedBlockingDeque<String> incomingMessage(){
        LinkedBlockingDeque<String> messageTunnel = new LinkedBlockingDeque<>();

        String inputLine;

        while (true) {
            try {
                if (!in.hasNext("")) {
                    inputLine = in.next();
                    accounts.acquireLock();
                    if (!inputLine.trim().isEmpty()) {
                        System.out.println("Incoming: " + inputLine);
                        messageTunnel.put(inputLine);
                    }
                }

                in.reset();

                return messageTunnel;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
