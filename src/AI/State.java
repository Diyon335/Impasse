package AI;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;
import GameClasses.GameBoard;
import GameClasses.GameManager;
import GameClasses.Player;
import GameClasses.Space;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class State implements Comparable<State>{

    private List<Move> moves;
    private GameBoard board;
    private HashMap<Piece,Space> boardMap;

    private Player player, opponent;


    //TODO make state param a game board, and piece to move and implement a static "copy game board" in gameboard class
    //TODO might have to make static copy of moves too (or maybe not since pieces in static board are new)
    public State (GameBoard board, Player player, Player opponent){

        this.board = board;
        this.player = player;
        this.opponent = opponent;

        this.boardMap = new HashMap<>();
        this.moves = new ArrayList<>();

        for (Piece piece : this.player.getPieces()){
            this.moves.addAll(piece.getLegalMoves());
        }
    }

    public List<State> getNextStates(){

        List<State> toReturn = new ArrayList<>();

        for (Move move : this.moves){
            GameBoard boardCopy = GameBoard.createCopy(this.board);
            move.movePiece(boardCopy);

            for (Piece piece : boardCopy.getPieces()){
                piece.updateLegalMoves(boardCopy);
            }

            toReturn.add(new State(boardCopy, this.opponent, this.player));
        }

        return toReturn;
    }



    public List<Move> getMoves(){
        return this.moves;
    }

    public boolean isTerminalState(){
        return this.board.getWhitePieces() == 0 || this.board.getBlackPieces() == 0;
    }

    public int evaluateState(){
        return EvaluationFunction.evaluate(this);
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     */
    @Override
    public int compareTo(State o) {

        if(this.moves.equals(o.getMoves())){
            return 0;
        }

        return -1;
    }
}
