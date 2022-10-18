package GameClasses;

import AbstractClasses.Piece;
import Enums.Colour;

import java.util.ArrayList;

public class Player {

    private Colour pieceColour;
    private ArrayList<Piece> pieces;
    private int amountOfPieces = 0;

    public Player (Colour pieceColour){
        this.pieceColour = pieceColour;
        this.pieces = new ArrayList<>();
    }

    /*public static Player copyPlayer(Player player, GameBoard board){

        System.out.println("Creating copy of player");
        Player toReturn = new Player(player.getPieceColour());

        for (Piece piece : player.getPieces()){

            if (piece instanceof Single){
                Single s = new Single(player.getPieceColour(), toReturn, new int[]{piece.getRow(), piece.getCol()});
                toReturn.addPiece(s);
                toReturn.removePiece(piece);
                s.updateLegalMoves(board);
            }

            if (piece instanceof DoublePiece){
                DoublePiece d = new DoublePiece(player.getPieceColour(), toReturn, new int[]{piece.getRow(), piece.getCol()});
                toReturn.addPiece(d);
                toReturn.removePiece(piece);
                d.updateLegalMoves(board);
            }
        }

        return toReturn;
    }*/

    public ArrayList<Piece> getPieces() {
        return this.pieces;
    }

    public int getAmountOfPieces(){
        return this.amountOfPieces;
    }

    public boolean hasBasicMoves(){
        for (Piece piece : this.pieces){
            if (piece.hasBasicMove()){
                return true;
            }
        }

        return false;
    }

    public boolean hasFreeSingles(){
        int count = 0;

        for (Piece piece : this.pieces){
            if (piece instanceof Single){
                count++;
            }
        }

        return count > 1;
    }

    public boolean isAtImpasse(){
        boolean canCrownOrBearOff = false;

        for (Piece piece : this.pieces){

            if (piece instanceof Single && ((Single) piece).canCrown() && hasFreeSingles()){
                canCrownOrBearOff = true;
                break;
            }

            if (piece instanceof DoublePiece && ((DoublePiece) piece).canBearOff()){
                canCrownOrBearOff = true;
                break;
            }
        }

        return !(hasBasicMoves() || canCrownOrBearOff);
    }

    public void addPiece(Piece piece){
        this.pieces.add(piece);
        this.amountOfPieces += piece.getCount();
    }

    public void removePiece(Piece piece){
        if (hasPiece(piece)){
            this.pieces.remove(piece);
            this.amountOfPieces-=piece.getCount();
        }

    }

    public boolean hasPiece(Piece piece){
        return this.pieces.contains(piece);
    }

    public Colour getPieceColour() {
        return this.pieceColour;
    }
}
