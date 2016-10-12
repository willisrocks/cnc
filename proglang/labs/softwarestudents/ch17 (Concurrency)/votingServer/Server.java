// This code was adapted by Allen Tucker in February, 2005
// out of programs downloaded from "The Java Tutorial" 
// http://java.sun.com/docs/books/tutorial/networking/sockets/clientServer.html
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
// 1. Initialize a shared variable to tally the votes.       
    	    TallyBuffer tally = new TallyBuffer();
        int port = 9600;
        ServerSocket serverSocket = null;
        ClientThread ct;
// 2. Open a new Socket for communicating with clients.
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
        } catch (IOException e) {
            System.err.println("Port: " + port + " unavailable.");
            System.exit(-1);
        }
// 3. Listen for connection requests from clients and start new Threads.
        while (true) {
        	   ct = new ClientThread(serverSocket.accept(), tally);
	       ct.start();
        }
    }
}
