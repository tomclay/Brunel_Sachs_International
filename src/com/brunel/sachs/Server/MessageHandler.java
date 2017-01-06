package com.brunel.sachs.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Tom Clay ESQ. on 05/01/2017.
 */
public class MessageHandler {

    private static Scanner incoming;
    private static PrintWriter outgoing;
    private static Socket socket;

    public MessageHandler(Socket s_socket){
        this.socket = s_socket;
        try {
            outgoing = new PrintWriter(socket.getOutputStream(), true);
            incoming = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message){
        System.out.println("Outgoing: " + message);
        outgoing.println(message);
        outgoing.write('\n');
        outgoing.flush();
    }

    public static LinkedBlockingDeque<String> incomingMessage(){
        LinkedBlockingDeque<String> messageTunnel = new LinkedBlockingDeque<>();

        String inputLine;

        while (true) {
            try {
                if (!incoming.hasNext("")) {
                    inputLine = incoming.next();
                    if (!inputLine.trim().isEmpty()) {
                        System.out.println("Incoming: " + inputLine);
                        messageTunnel.put(inputLine);
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

