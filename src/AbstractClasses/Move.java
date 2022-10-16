package AbstractClasses;

import AbstractClasses.Piece;
import GameClasses.GameBoard;
import GameClasses.GameManager;
import GameClasses.Player;
import GameClasses.Space;

public abstract class Move implements Comparable<Move> {

    private Space from;
    private Space to;
    private Piece movingPiece;

    public Move (Piece movingPiece, Space from, Space to){
        this.movingPiece = movingPiece;
        this.from = from;
        this.to = to;
    }

    public Space getFrom() {
        return this.from;
    }

    public Space getTo(){
        return this.to;
    }

    public Piece getMovingPiece() {
        return this.movingPiece;
    }

    public abstract boolean isBasicMove();

    public abstract boolean canBeApplied();

    public abstract void movePiece(GameManager gameManager);

    public abstract void movePiece(GameBoard board);

    @Override
    public String toString(){
        return this.movingPiece.getColour()+" moved from "+this.from+" to "+this.to;
    }

    @Override
    public int compareTo(Move move){

        if (this.to.compareTo(move.getTo()) == 0 && this.from.compareTo(move.getFrom()) == 0){
            return 0;
        }

        return -1;
    }
}
