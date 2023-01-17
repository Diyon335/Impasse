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

        long start = System.currentTimeMillis();

        int treeDepth = 5;
        int searchDepth = 5;
        GameManager game = new GameManager(8,8, false, false, treeDepth, searchDepth);
        game.play();

        long end = System.currentTimeMillis();
        System.out.println("Time elapsed: "+(end - start)/1000);

    }
}
