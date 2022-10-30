package AbstractClasses;

import Enums.Colour;
import GameClasses.GameBoard;
import GameClasses.GameManager;
import GameClasses.Player;
import GameClasses.Space;

import java.util.ArrayList;

/**
 * Abstract class for pieces
 */
public abstract class Piece implements Comparable<Piece>{

    private Colour colour;
    private int[] position;
    private GameManager gameManager;
    private ArrayList<Move> moves;
    private Player player;

    /**
     * A constructor for the piece class
     * @param colour The colour of the piece
     * @param player The player of the piece
     * @param position An integer array for the position of the piece
     * @param gameManager The game manager
     */
    public Piece(Colour colour, Player player, int[] position, GameManager gameManager){
        this(colour, player, position);
        this.gameManager = gameManager;
    }

    /**
     * A constructor for the piece class
     * @param colour The colour of the piece
     * @param player The player of the piece
     * @param position An integer array for the position of the piece
     */
    public Piece(Colour colour, Player player, int[] position){
        this.colour = colour;
        this.player = player;
        this.position = position;
        this.moves = new ArrayList<>();
    }

    /**
     * Gets the player
     * @return Returns the player
     */
    public Player getPlayer(){return this.player;}

    /**
     * Adds a move to the piece's legal moves
     * @param move Move to be added
     */
    public void addMove(Move move){
        this.moves.add(move);
    }

    /**
     * Gets the colour of the piece
     * @return Returns the colour of the piece
     */
    public Colour getColour() {
        return this.colour;
    }

    /**
     * Gets a move from the piece's list of legal moves
     * @param from Space from which the move starts
     * @param to Space to which the piece moves to
     * @return Returns a move
     */
    public Move getMove(Space from, Space to){
        for (Move move : this.moves){
            if (move.getFrom().compareTo(from) == 0 && move.getTo().compareTo(to) == 0){
                return move;
            }
        }

        return null;
    }

    /**
     * Gets the row of the piece
     * @return Returns an integer indicating the row of the piece
     */
    public int getRow(){
        return this.position[0];
    }

    /**
     * Gets the column of the piece
     * @return Returns an integer indicating the column of the piece
     */
    public int getCol(){
        return this.position[1];
    }

    /**
     * Gets the position of the piece
     * @return Returns an integer array with the row and column of the piece
     */
    public int[] getPosition(){
        return this.position;
    }

    /**
     * Sets the new position of the piece
     * @param row Row to be set
     * @param column Column to be set
     */
    public void setPosition(int row, int column){
        this.position[0] = row;
        this.position[1] = column;
    }

    /**
     * Gets the list of legal moves of the piece
     * @return Returns an array list with all legal moves
     */
    public ArrayList<Move> getLegalMoves() {
        return this.moves;
    }

    /**
     * Clears all known moves
     */
    public void clearMoves(){
        this.moves.clear();
    }

    /**
     * Indicates if the piece is white
     * @return Returns a boolean indicating if the piece is white
     */
    public boolean isWhite(){
        return this.colour == Colour.WHITE;
    }

    /**
     * Indicates whether the piece can be moved to a particular space
     * @param space Space to check if can the piece can move to
     * @return Returns a boolean indicating if the move has the space
     */
    public boolean hasLegalSpace(Space space){
        for (Move move : this.moves){
            if (move.getTo().equals(space)){
                return true;
            }
        }

        return false;
    }

    /**
     * Indicates whether the piece has basic moves
     * @return Returns a boolean indicating if the piece has basic moves
     */
    public boolean hasBasicMove(){
        for (Move move : this.moves){
            if (move.isBasicMove()){
                return true;
            }
        }

        return false;
    }

    /**
     * Updates all legal moves
     * @param board Game board on which moves should be updated
     */
    public abstract void updateLegalMoves(GameBoard board);

    /**
     * Gets the count of the piece. Single = 1 piece. DoublePiece = 2 pieces
     * @return Returns an integer which represents the count of pieces within the object
     */
    public abstract int getCount();

    /**
     * Compares a piece to another
     * @param piece Piece to be compared to
     * @return Returns an integer. 0 if the same, -1 if not
     */
    @Override
    public int compareTo(Piece piece){

        if (this.colour != piece.getColour()){
            return -1;
        }

        if (this.moves.size() != piece.getLegalMoves().size()){
            return -1;
        }

        for (int i = 0; i < this.moves.size(); i++){

            if (this.moves.get(i).compareTo(piece.getLegalMoves().get(i)) != 0){
                return -1;
            }
        }

        if (this.position[0] != piece.getRow() || this.position[1] != piece.getCol()){
            return -1;
        }

        return 0;
    }
}
