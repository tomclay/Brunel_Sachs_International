package com.brunel.sachs.Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ActionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 4545;
        String ActionServerName = "localhost";
        String ActionClientID = "client2";

        try {
            ActionClientSocket = new Socket(ActionServerName, ActionSocketNumber);
            out = new PrintWriter(ActionClientSocket.getOutputStream(), true);
            // in = new BufferedReader(new InputStreamReader(ActionClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ ActionSocketNumber);
            System.exit(1);
        }

        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + ActionClientID + " Client and IO connections");

        // This is modified as it's the Client that speaks first

        while (true) {

            Scanner test = new Scanner(ActionClientSocket.getInputStream());
            String input;
            input = test.nextLine();

            //fromServer = in.readLine();
            if(!input.trim().isEmpty()) {
                System.out.println(ActionClientID + " received " + input + " from ActionServer");
            }

            Scanner kb = new Scanner(System.in);
            String random;
            random = kb.next();

            // fromUser = stdIn.readLine();
            if (!random.trim().isEmpty()) {
                System.out.println(ActionClientID + " sending " + random + " to ActionServer");
                out.println(random + "\n");
            }
        }


        // Tidy up - not really needed due to true condition in while loop
        //  out.close();
        // in.close();
        // stdIn.close();
        // ActionClientSocket.close();
    }
}
