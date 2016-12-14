package equity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Tom Clay ESQ. on 12/12/2016.
 */
public class Client {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int serverSocketNumber = 4545;
        String serverName = "localhost";
        String clientID = "client1";

        try {
            clientSocket = new Socket(serverName, serverSocketNumber);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + clientID + " client and IO connections");

        // This is modified as it's the client that speaks first

        while (true) {

            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(clientID + " sending " + fromUser + " to ActionServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(clientID + " received " + fromServer + " from ActionServer");
        }


        // Tidy up - not really needed due to true condition in while loop
        //  out.close();
        // in.close();
        // stdIn.close();
        // clientSocket.close();
    }
}