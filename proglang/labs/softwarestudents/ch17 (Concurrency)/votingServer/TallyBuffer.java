public class TallyBuffer {
     
	int[] tally = {0,0,0,0};
	
	public synchronized void update(int[] aBallot) {
		for (int i=0; i<tally.length; i++)
		   tally[i] = tally[i] + aBallot[i];
	}
	
	public synchronized void display() {
		System.out.println("Current voting tally: ");
		for (int i=0; i<tally.length; i++)
		   System.out.println("  candidate " + (i+1) + ". " + tally[i]);
	}
}
