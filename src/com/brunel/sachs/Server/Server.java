package com.brunel.sachs.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * Created by Tom Clay ESQ. on 12/12/2016.
 */
public class Server {

    public static void main(String[] args) throws IOException {


        ServerSocket MainframeServerSocket = null;
        boolean listening = true;
        String MainframeName = "BrunelMainframe";
        int portNumber = 4545;


        //Create the shared object in the global scope...
        Account sharedAccount = new Account();

        // Make the Server socket
        try {
            MainframeServerSocket = new ServerSocket(portNumber);


        } catch (IOException e) {
            System.err.println("Could not start " + MainframeName + " specified port.");
            System.exit(-1);
        }
        System.out.println(MainframeName + " started");

        //Got to do this in the correct order with only four clients!  Can automate this...
        while (listening){
            new Transaction(MainframeServerSocket.accept(), sharedAccount,  "ServerThread1").start();
            new Transaction(MainframeServerSocket.accept(), sharedAccount,  "ServerThread2").start();
            new Transaction(MainframeServerSocket.accept(), sharedAccount,  "ServerThread3").start();
            new Transaction(MainframeServerSocket.accept(), sharedAccount,  "ServerThread4").start();

            System.out.println("New " + MainframeName + " thread started.");
        }
        MainframeServerSocket.close();
    }


}
