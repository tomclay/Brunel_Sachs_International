package com.brunel.sachs.Client;

import com.brunel.sachs.Server.transactionLogging;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import static java.lang.Thread.sleep;


public class Client4 {
    public static void main(String[] args) throws IOException {

        /**
         * This class handles the connection to the server, and send/receive messages.
         *
         * Two separate blocks handle human input, and simulated input for testing.
         */

        Socket clientSocket = null;
        int socketNumber = 4545;
        String serverName = "localhost", clientID = "client4";
        Scanner in, clientInput;
        PrintWriter out;
        boolean person = true;

        try {
            // Setup connections and I/O scanners
            clientSocket = new Socket(serverName, socketNumber);

        } catch (UnknownHostException e) {
            System.err.println("Cannot find this server.");
            System.exit(1);

        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + socketNumber);
            System.exit(1);
        }

        System.out.println("Initialised " + clientID + " Client and I/O connections");

        /**
         * Two code blocks handing real input and tests
         */

        if (person) {

            /**
             * This while loop is based on the server sending the first message then following that conversational patter (server-client-server-...).
             * The Scanners wait until there is a next character then process the message accordingly.
             */

            while (true) {
                in = new Scanner(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                clientInput = new Scanner(System.in);

                String serverInput = in.nextLine();
                if (!serverInput.trim().isEmpty()) {
                    transactionLogging.log(Level.INFO, clientID + " received " + serverInput + " from Brunel Sachs");
                }

                String terminalInput = clientInput.next();
                if (!terminalInput.trim().isEmpty()) {
                    transactionLogging.log(Level.INFO, clientID + " sending " + terminalInput + " to Brunel Sachs");
                    out.println(terminalInput);
                }
            }

        } else {

            /**
             * This while loop simulates a human user by using programmed responses and sending them to the server.
             */

            // Responses storage
            String[] responses = new String[3];
            // Add the responses here
            responses[0] = "1";
            responses[1] = "2";
            responses[2] = "100";

            // Limit the number of iterations
            int count = 0;
            while (true) {
                in = new Scanner(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                String serverInput = in.nextLine();

                if (!serverInput.trim().isEmpty()) {
                    transactionLogging.log(Level.INFO, clientID + " received " + serverInput + " from Brunel Sachs");
                }

                // Stop if there are no more responses
                if (!(count < responses.length)) {
                    break;
                }

                transactionLogging.log(Level.INFO, clientID + " sending " + responses[count] + " to Brunel Sachs");
                out.println(responses[count]);
                count++;

                try {
                    // Separate out the commands with a small gap
                    sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}