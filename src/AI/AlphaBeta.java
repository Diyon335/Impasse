package AI;

import AbstractClasses.Move;
import GameClasses.GameBoard;
import GameClasses.GameManager;
import GameClasses.Player;

public class AlphaBeta {

    private GameManager gameManager;

    public AlphaBeta(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public Move minMax(Player player, Player opponent, int depth, boolean orderMoves){

        GameBoard board = GameBoard.createCopy(this.gameManager.getBoard());
        State currentState = new State(board, player, opponent);
        Tree tree = new Tree(currentState, depth, player.getPieceColour(), orderMoves);





        return null;
    }
}
