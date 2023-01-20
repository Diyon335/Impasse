package AbstractClasses;

import GameClasses.GameBoard;
import GameClasses.Space;

/**
 * Abstract class for Moves
 */
public abstract class Move implements Comparable<Move> {

    private Space from;
    private Space to;
    private Piece movingPiece;

    /**
     * Constructor for a move
     * @param movingPiece Piece to move
     * @param from Space from which the piece moves
     * @param to Space to which the piece will move
     */
    public Move (Piece movingPiece, Space from, Space to){
        this.movingPiece = movingPiece;
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the space from which the piece moves
     * @return Returns a space object from which the piece moves
     */
    public Space getFrom() {
        return this.from;
    }

    /**
     * Gets the space to which the piece moves
     * @return Returns a space object to which the piece moves
     */
    public Space getTo(){
        return this.to;
    }

    /**
     * Gets the moving piece
     * @return Returns the piece object that moves
     */
    public Piece getMovingPiece() {
        return this.movingPiece;
    }

    /**
     * Indicates whether the move is a basic move
     * @return Returns a boolean indicating whether the move is a basic move
     */
    public abstract boolean isBasicMove();

    /**
     * Indicates whether the move can be applied
     * @return Returns a boolean indicating if the move can be applied
     */
    public abstract boolean canBeApplied();

    /**
     * Moves the piece on the board within a game
     * @param board Game board for the game
     */
    public abstract void movePiece(GameBoard board);

    /**
     * Overridden toString method
     * @return Returns a string representation of the move
     */
    @Override
    public String toString(){
        return this.movingPiece.getColour()+" moved from "+this.from+" to "+this.to;
    }

    /**
     * Compares a move to another. Returns 0 if the moves are the same
     * @param move Move to be compared with
     * @return Returns an integer. 0 if they are the same object, -1 if not
     */
    @Override
    public int compareTo(Move move){

        if (this.to.compareTo(move.getTo()) == 0 && this.from.compareTo(move.getFrom()) == 0 && this.movingPiece.getColour() == move.getMovingPiece().getColour()){
            return 0;
        }

        return -1;
    }
}
