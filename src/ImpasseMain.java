import GameClasses.GameManager;

/**
 * The impasse game
 * @author (Game concept) Mark Steere
 * @author (Programming) Diyon Wickrameratne (i6176139)
 */
@SuppressWarnings("all")
public class ImpasseMain {

    /**
     * The main method for running the game
     * @param args A string of arguments
     */
    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        boolean p1IsAI = true;
        boolean p2IsAI = true;
        int idTime = 10;

        GameManager game = new GameManager(8,8, p1IsAI, p2IsAI, idTime);
        game.play();

        long end = System.currentTimeMillis();
        
        if (p1IsAI && p2IsAI) System.out.println("Time elapsed: "+(end - start)/1000);

    }
}
