package AI;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;
import GameClasses.DoublePiece;
import GameClasses.GameBoard;
import GameClasses.Player;
import GameClasses.Single;
import Moves.*;

public class EvaluationFunction {


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
