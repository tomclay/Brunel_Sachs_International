package equity;

import javax.swing.text.html.BlockView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Tom Clay ESQ. on 12/12/2016.
 */

public class accessThread extends Thread {

    private Socket serverSocket = null;
    private Accounts accounts;
    private String localServerThreadName;
    private PrintWriter out;

    //Setup the thread
    public accessThread(Socket actionSocket, String ServerThreadName, Accounts accountInstance) {
        this.serverSocket = actionSocket;
        accounts = accountInstance;
        localServerThreadName = ServerThreadName;
    }

    public void run() {
        try {

            BlockingQueue<String> messageTunnel = accounts.messageTunnel;

            System.out.println(localServerThreadName + " initialising.");
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            System.out.println(localServerThreadName + " initialised.");
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {

                System.out.println(inputLine);
            }

            out.close();
            in.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(String input){
        out.println(input);
    }
}
