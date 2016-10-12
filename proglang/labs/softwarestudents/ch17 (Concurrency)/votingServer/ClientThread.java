import java.net.*;
import java.io.*;

public class ClientThread extends Thread {
    private Socket socket = null;
    private TallyBuffer tally;

    public ClientThread(Socket socket, TallyBuffer tally) {
	    super("ClientThread");
	    this.socket = socket;
	    this.tally = tally;
    }

    public void run() {

	try { System.out.println("Voter starting");
				
	//1. Open a new input and output stream with the client.
		PrintWriter out = new PrintWriter(
	    	                 socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(
		                 new InputStreamReader(socket.getInputStream()));
	
	// 2. Initialize a new ballot and protocol for interacting with the voter.
	    String inputLine, outputLine;
	    ClientProtocol voter = new ClientProtocol();
	    outputLine = voter.processInput(null, tally);
	    out.println(outputLine); 
	
	// 3. Loop to read and process individual votes from the voter.
	    while ((inputLine = in.readLine()) != null) { 
		   outputLine = voter.processInput(inputLine, tally);
		   out.println(outputLine);    // relay message to client
		   if (outputLine.equals("Bye"))
		      break;
	 	}
	    System.out.println("\nVoter finishing");
	    tally.display(); 
	
	// 4. Close the two streams, and finally the socket
	    out.close();
	    in.close();
	    socket.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
