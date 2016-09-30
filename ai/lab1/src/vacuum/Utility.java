package vacuum;
import java.util.Random;
import java.util.List;

/**
 * Created by chris on 9/29/16.
 */
public class Utility {

    static Random rand = new Random();
    public static <T> T getRandomItem(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }

    public static Action rand() {
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

}
