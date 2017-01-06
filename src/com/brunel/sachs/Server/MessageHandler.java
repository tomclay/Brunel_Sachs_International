package com.brunel.sachs.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;

/**
 * Created by Tom Clay ESQ.
 */

public class MessageHandler {

    /**
     * A class to handle sending an receiving messages from the client
     */

    private static Scanner incoming;
    private static PrintWriter outgoing;
    private static Socket socket;

    /**
     * The constructor to initialise the incoming and outgoing streams to the socket
     *
     * @param s_socket The socket to connect with the client
     */

    public MessageHandler(Socket s_socket) {

        this.socket = s_socket;
        try {
            outgoing = new PrintWriter(socket.getOutputStream(), true);
            incoming = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method to sent a message to the client
     *
     * @param message The message to be sent
     */

    public static void sendMessage(String message) {

        System.out.println("Outgoing: " + message);
        outgoing.println(message);
        outgoing.flush();
    }

    /**
     * A method to receive messages from the client. When the system expects a message, this method is called.
     * The input scanner waits until there is some input from the socket
     *
     * Note that a LinkedBlockingDeque has been used as a thread safe data transport mechanism
     *
     * @return messageTunnel The LinkedBlockingDeque of length 1 containing the message
     */

    public static LinkedBlockingDeque<String> incomingMessage() {

        LinkedBlockingDeque<String> messageTunnel = new LinkedBlockingDeque<>();
        String inputLine;
        // Run this loop until something arrives from the client
        while (true) {
            try {
                if (!incoming.hasNext("")) { // Don't do anything unless there's input
                    inputLine = incoming.next(); // Set inputLine to the socket input
                    if (!inputLine.trim().isEmpty()) {
                        transactionLogging.log(Level.INFO,"Incoming: " + inputLine);
                        messageTunnel.put(inputLine); // Put message into structure
                    }
                }
                incoming.reset();
                return messageTunnel;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

