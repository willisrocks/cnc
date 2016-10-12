//This code was adapted by Allen Tucker in February, 2005
//out of programs downloaded from "The Java Tutorial" 
//http://java.sun.com/docs/books/tutorial/networking/sockets/clientServer.html
public class ClientProtocol {
    private static final int WAITING = 0;
    private static final int SENTBALLOT = 1;
    private static final int WANTANOTHER = 2;
    private static final int DONE = 3;
    
    private int state = WAITING;
    int[] ballot = {0,0,0,0};
    
    public String processInput(String response, TallyBuffer tally) {
        String theOutput = "";
        if (state == WAITING) {
            theOutput = "Vote for one or more: 1. Allen 2. Bob 3. Alan 4. Rebecca" ;
            state = SENTBALLOT;
        } 
		else if (state == SENTBALLOT) 
            if (response.length() > 0 && response.compareTo("1") >= 0 && 
            		response.compareTo("4") <= 0) {
            	   int n = (new Integer(response)).intValue();
            	   if (ballot[n-1] == 0) {
            	   	   ballot[n-1] = 1;
            	   	   theOutput = " Do you want another vote? (y/n)";
                    state = WANTANOTHER; 
            	   }
            	   else theOutput = "Can't vote twice for the same person: try again.";
            }
            else theOutput = "Invalid number: try again.";
         else if (state == WANTANOTHER){
                if (response.equalsIgnoreCase("y")) {
                	  theOutput = "Enter another number: ";
                   state = SENTBALLOT;
                }
                else {
                	  state = DONE;
                	  tally.update(ballot);  // cast the ballot
                	  theOutput = "Bye";
                } 	  
         }
         else // state == DONE 
                theOutput = "Bye";
         return theOutput;
    }
}
