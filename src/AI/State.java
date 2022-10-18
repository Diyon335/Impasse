package AI;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;
import GameClasses.*;
import Moves.BearOff;
import Moves.Crown;
import Moves.Impasse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class State implements Comparable<State>{

    private List<Move> moves;
    private GameBoard board;
    private HashMap<Piece,Space> boardMap;

    private Player player, opponent;

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

    public Player getPlayer(){
        return this.player;
    }

    public Player getOpponent(){
        return this.opponent;
    }

    public int possibleNextStates(){
        return this.moves.size();
    }

    public List<State> getNextStates(){

        List<State> toReturn = new ArrayList<>();

        for (Move move : this.moves){

            if (move.canBeApplied()){

                GameBoard boardCopy = GameBoard.createCopy(this.board);
                move.movePiece(boardCopy);

                for (Piece piece : boardCopy.getPieces()){
                    piece.updateLegalMoves(boardCopy);
                }

                Player player = this.player.getPieceColour() == Colour.WHITE ? boardCopy.getWhite() : boardCopy.getBlack();
                Player opponent = player.getPieceColour() == Colour.WHITE ? boardCopy.getBlack() : boardCopy.getWhite();

                if (move instanceof Crown){
                    toReturn.add(new State(boardCopy, player, player));

                } else if (move instanceof BearOff){
                    toReturn.add(new State(boardCopy, player, player));

                } else if (move instanceof Impasse){
                    toReturn.add(new State(boardCopy, player, player));

                } else {
                    toReturn.add(new State(boardCopy, opponent, player));
                }
            }
        }

        return toReturn;
    }


    public GameBoard getBoard(){
        return this.board;
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

    @Override
    public String toString(){
        return this.board.toString();
    }
}
