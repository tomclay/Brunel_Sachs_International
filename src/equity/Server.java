package equity;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Tom Clay ESQ. on 12/12/2016.
 */
public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket MainframeServerSocket = null;
        boolean listening = true;
        String MainframeName = "BrunelMainframe";
        int portNumber = 4545;
        BlockingQueue<String> messageTunnel = new LinkedBlockingQueue<>();


        //Create the shared object in the global scope...
        Accounts sharedAccounts = new Accounts(messageTunnel);

        // Make the server socket
        try {
            MainframeServerSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.err.println("Could not start " + MainframeName + " specified port.");
            System.exit(-1);
        }
        System.out.println(MainframeName + " started");

        //Got to do this in the correct order with only four clients!  Can automate this...
        while (listening){
            new accessThread(MainframeServerSocket.accept(), "ServerThread1", sharedAccounts).start();
            new accessThread(MainframeServerSocket.accept(), "ServerThread2", sharedAccounts).start();
            new accessThread(MainframeServerSocket.accept(), "ServerThread3", sharedAccounts).start();
            new accessThread(MainframeServerSocket.accept(), "ServerThread4", sharedAccounts).start();
            System.out.println("New " + MainframeName + " thread started.");
        }
        MainframeServerSocket.close();
    }


}
