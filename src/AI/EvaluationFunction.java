package AI;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;
import GameClasses.DoublePiece;
import GameClasses.GameBoard;
import GameClasses.Player;
import GameClasses.Single;
import Moves.*;

/**
 * Class for the evaluation function
 */
public class EvaluationFunction {


    /**
     * The evaluation function that evaluates a state depending on the player
     *
     * Terminal states are much more desired, therefore given the highest values.
     * Moves that bring singles to the furthest row, and doubles to the nearest row are given more weight
     * Transposes are given a slightly higher weight - much more if the resulting transpose permits a bear off
     * States that result in impasses are also given a high weight
     *
     * @param state State to be evaluated
     * @param maxPlayer Boolean to indicate if max or min player
     * @return Returns an integer that represents the score of the state after evaluation
     */
    public static int evaluate(State state, boolean maxPlayer){

        if (state.isTerminalState()){

            return maxPlayer ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        int score = 0;

        int furthestRow = maxPlayer ? 0 : 7;
        int nearestRow = maxPlayer ? 7 : 0;

        int bearOffCrown = maxPlayer ? 100 : -100;
        int rowDiffWeight = maxPlayer ? 20 : -20;
        int impasse = maxPlayer ? 500 : -500;

        int transpose = maxPlayer ? 5 : -5;

        Move move = state.getBoard().getLastMovePlayed();
        Piece piece = move.getMovingPiece();

        if ((move instanceof Slide) || (move instanceof Step)){
            int toRow = move.getTo().getRow();
            int rowDiff = (piece instanceof Single) ? Math.abs(toRow - furthestRow) : Math.abs(toRow - nearestRow);

            if (rowDiff < 1){
                score += bearOffCrown;
            }

            rowDiff = Math.max(rowDiff, 1);

            score += rowDiffWeight / rowDiff;

            GameBoard board = state.getBoard();
            GameBoard boardCopy = new GameBoard(board.getSize(), board.getSize());
            boardCopy.copyBoardFrom(board);
            boardCopy.setTurn(boardCopy.getOpponent());

            int moves = 0;
            for (Piece p : boardCopy.getTurn().getPieces()){
                moves += p.getLegalMoves().size();
            }

            if (moves == 0){
                score += impasse;
            }
        }

        if (move instanceof Transpose){

            if (move.getTo().getRow() == nearestRow){
                score+=bearOffCrown;
            }

            score += transpose;
        }


        return score;
    }
}
