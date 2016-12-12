package equity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Tom Clay ESQ. on 12/12/2016.
 */

public class Thread extends java.lang.Thread {

    private Socket serverSocket = null;
    private Accounts accounts;
    private String localServerThreadName;

    //Setup the thread
    public Thread(Socket actionSocket, String ServerThreadName, Accounts accountInstance) {
        this.serverSocket = actionSocket;
        accounts = accountInstance;
        localServerThreadName = ServerThreadName;
    }

    public void run() {
        try {
            System.out.println(localServerThreadName + "initialising.");
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                // Get a lock first
                try {
                    accounts.acquireLock();
                    outputLine = accounts.processInput(localServerThreadName, inputLine);
                    out.println(outputLine);
                    accounts.releaseLock();
                }
                catch(InterruptedException e) {
                    System.err.println("Failed to get lock when reading:"+e);
                }
            }

            out.close();
            in.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
