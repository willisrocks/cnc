package vacuum;
import java.util.Random;

/** An agent for the	vacuum world. */
public class RandomAgent extends	AbstractAgent{

   /**
    *	Returns the	agent's action in response to the dirtiness state of the
    *	current square.
    */
   public  Action	react(boolean dirty)	{
      if	(dirty) {
         return Action.SUCK;
      }
      return randomAction();
   }

   /**
    *	Returns a random action.
    */
   private Action randomAction() {
      Random gen = new Random();
      int randInt = gen.nextInt(4);
      switch (randInt) {
         case 0: return Action.LEFT;
         case 1: return Action.UP;
         case 2: return Action.RIGHT;
         case 3: return Action.DOWN;
      }
      return Action.LEFT;
   }

   public void sense_obstacle(boolean isObstacle){

   }

}
