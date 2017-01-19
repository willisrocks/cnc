// This code was adapted by Allen Tucker in February, 2005
// out of programs downloaded from "The Java Tutorial" 
// http://java.sun.com/docs/books/tutorial/networking/sockets/clientServer.html
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket mySocket;
        PrintWriter out;
        BufferedReader in;
        BufferedReader stdIn;
        String hostName = args[0];
        int port = 9600;

        try {
        	// 1. Open a socket.
        	   mySocket = new Socket(hostName, port);

        // 2. Open input and output streams to socket and user.
        	   out = new PrintWriter(mySocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
            			mySocket.getInputStream()));
            stdIn = new BufferedReader(
        			    new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
        // 3. Loop to relay messages from client thread to user, and 
        //    from user to client thread.
            while ((fromServer = in.readLine()) != null) {
               System.out.println("> " + fromServer);
               if (fromServer.equals("Bye"))
                   break;		    
               fromUser = stdIn.readLine();
	           if (fromUser != null) {
                   out.println(fromUser);
	           }
            }
            out.close();
            in.close();
            stdIn.close();
            mySocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Invalid host name: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException with host: " + hostName);
            System.exit(1);
        }
    }
}
