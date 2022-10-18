package AbstractClasses;

import Enums.Colour;
import GameClasses.GameBoard;
import GameClasses.GameManager;
import GameClasses.Player;
import GameClasses.Space;
import Moves.Step;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Piece implements Comparable<Piece>{

    private Colour colour;
    private int[] position;
    private GameManager gameManager;
    private ArrayList<Move> moves;
    private Player player;

    public Piece(Colour colour, Player player, int[] position, GameManager gameManager){
        this(colour, player, position);
        this.gameManager = gameManager;
    }

    public Piece(Colour colour, Player player, int[] position){
        this.colour = colour;
        this.player = player;
        this.position = position;
        this.moves = new ArrayList<>();
    }

    public Player getPlayer(){return this.player;}

    public void addMove(Move move){
        this.moves.add(move);
    }

    public boolean hasMove(Move move){
        return this.moves.contains(move);
    }

    protected GameManager getGameManager(){
        return this.gameManager;
    }

    public Colour getColour() {
        return this.colour;
    }

    public void removeMove(Move move){
        this.moves.remove(move);
    }

    public Move getMove(Space from, Space to){
        for (Move move : this.moves){
            if (move.getFrom().equals(from) && move.getTo().equals(to)){
                return move;
            }
        }

        return null;
    }

    public int getRow(){
        return this.position[0];
    }

    public int getCol(){
        return this.position[1];
    }

    public int[] getPosition(){
        return this.position;
    }

    public void setPosition(int row, int column){
        this.position[0] = row;
        this.position[1] = column;
    }

    public ArrayList<Move> getLegalMoves() {
        return this.moves;
    }

    public void clearMoves(){
        if (this.moves.size() > 0){
            this.moves.clear();
        }
    }

    public boolean isWhite(){
        return this.colour == Colour.WHITE;
    }

    public boolean hasLegalSpace(Space space){
        for (Move move : this.moves){
            if (move.getTo().equals(space)){
                return true;
            }
        }

        return false;
    }

    public boolean hasBasicMove(){
        for (Move move : this.moves){
            if (move.isBasicMove()){
                return true;
            }
        }

        return false;
    }

    public abstract void updateLegalMoves(GameBoard board);

    public abstract int getCount();

    @Override
    public int compareTo(Piece piece){
        if (this.colour == piece.getColour() && this.moves.equals(piece.getLegalMoves()) && Arrays.equals(this.position, piece.getPosition())){
            return 0;
        }

        return -1;
    }
}
