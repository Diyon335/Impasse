package AI;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;
import GameClasses.DoublePiece;
import GameClasses.Player;
import GameClasses.Single;
import Moves.BearOff;
import Moves.Crown;
import Moves.Impasse;
import Moves.Transpose;

public class EvaluationFunction {


    public static int evaluate(State state, boolean maxPlayer){

        //Player player = state.getPlayer();
        //Player opponent = state.getOpponent();
        //Player player = state.getOpponent();
        //Player opponent = state.getPlayer();

        if (state.isTerminalState()){

            return maxPlayer ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        int score = 0;

        int furthestRow = maxPlayer ? 0 : 7;
        int nearestRow = maxPlayer ? 7 : 0;

        int pieceDiff = maxPlayer ? 100 : -100;
        int rowDiffWeight = maxPlayer ? 20 : -20;

        Move move = state.getBoard().getLastMovePlayed();
        Piece piece = move.getMovingPiece();
        int toRow = move.getTo().getRow();
        int rowDiff = (piece instanceof Single) ? Math.abs(toRow - furthestRow) : Math.abs(toRow - nearestRow);

        rowDiff = Math.max(rowDiff, 1);

        score += rowDiffWeight / rowDiff;

        Move lastPlayedMove = state.getBoard().getLastMovePlayed();

        Player player = (lastPlayedMove instanceof Crown) || (lastPlayedMove instanceof BearOff) ? state.getPlayer() : state.getOpponent();
        Player opponent = (lastPlayedMove instanceof Crown) || (lastPlayedMove instanceof BearOff) ? state.getOpponent() : state.getPlayer();

        if (player.getAmountOfPieces() < opponent.getAmountOfPieces()){
            score += pieceDiff;
        }

        return score;
    }
}
