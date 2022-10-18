import Enums.GameState;
import GameClasses.GameManager;

/**
 * The impasse game
 * @author (Game concept) Mark Steere
 * @author (Programming) Diyon Wickrameratne (i6176139)
 */

public class ImpasseMain {

    /**
     * The main method for running the game
     * @param args A string of arguments
     */
    public static void main(String[] args) {

        GameManager game = new GameManager(8,8, true, true);

        game.play();

    }
}
