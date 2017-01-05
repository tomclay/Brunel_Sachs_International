package com.brunel.sachs.Server;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Tom Clay ESQ. on 05/01/2017.
 */
public class MessageHandler {

    private static Scanner incoming;
    private static PrintWriter outgoing;
    private static Account account;
    private static Transaction transaction;

    public MessageHandler(Scanner in, PrintWriter out, Account acc){
        this.incoming = in;
        this.outgoing = out;
        this.account = acc;
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
                    account.acquireLock();
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

