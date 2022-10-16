package GameClasses;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;

import java.util.ArrayList;

public class GameBoard {

    private Space[][] board;
    private int rows, cols;
    private ArrayList<Piece> pieces;
    private int whitePieces, blackPieces;

    public GameBoard(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.board = new Space[rows][cols];
        this.pieces = new ArrayList<>();
        this.whitePieces = 0;
        this.blackPieces = 0;

        for (int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (i%2 != 0){
                    if (j%2 != 0){
                        this.board[i][j] = new Space(null, new int[]{i,j}, Colour.WHITE);
                    } else {
                        this.board[i][j] = new Space(null, new int[]{i,j}, Colour.BLACK);
                    }
                } else {
                    if (j%2 != 0){
                        this.board[i][j] = new Space(null, new int[]{i,j}, Colour.BLACK);
                    } else {
                        this.board[i][j] = new Space(null, new int[]{i,j}, Colour.WHITE);
                    }
                }

            }
        }
    }

    public int getWhitePieces(){
        return this.whitePieces;
    }

    public int getBlackPieces(){
        return this.blackPieces;
    }

    public void addPiece(Piece piece, Player player){

        if (player != null){
            player.addPiece(piece);
        }

        if (piece.getColour() == Colour.WHITE){
            this.whitePieces+=piece.getCount();
        } else {
            this.blackPieces+=piece.getCount();
        }

        this.pieces.add(piece);
    }

    public void removePiece(Piece piece, Player player){

        if (player != null){
            player.removePiece(piece);
        }

        if (piece.getColour() == Colour.WHITE){
            this.whitePieces-=piece.getCount();
        } else {
            this.blackPieces-=piece.getCount();
        }

        this.pieces.remove(piece);
    }

    public Piece getPieceAt(Space space){
        return this.board[space.getRow()][space.getCol()].getPiece();
    }

    public ArrayList<Piece> getPieces(){
        return this.pieces;
    }

    public int getSize(){
        return this.rows;
    }

    public Space[] getRow(int row){
        return this.board[row];
    }

    public Space getSpace(int[] index){
        return this.board[index[0]][index[1]];
    }

    public Space getSpace(int row, int col){
        return this.board[row][col];
    }

    public boolean isOnBoard(int[] index){
        return (index[0]>=0 && index[0]<this.rows) && (index[1]>=0 && index[1]<this.cols);
    }

    public ArrayList<Piece> getSinglesInFurthestRow(Player player){

        ArrayList<Piece> singles = new ArrayList<>();

        for (Space space : this.board[getFurthestRow(player)]){
            if ((space.getPiece() instanceof Single) && player.hasPiece(space.getPiece())){
                singles.add(space.getPiece());
            }
        }

        return singles;
    }

    public boolean hasSingleInFurthestRow(Player player){
        return getSinglesInFurthestRow(player).size() > 0;
    }

    public ArrayList<Piece> getDoublesInNearestRow(Player player){

        ArrayList<Piece> doubles = new ArrayList<>();

        for (Space space : this.board[getNearestRow(player)]){
            if ((space.getPiece() instanceof DoublePiece) && player.hasPiece(space.getPiece())){
                doubles.add(space.getPiece());
            }
        }

        return doubles;
    }


    public boolean hasDoublesInNearestRow(Player player){
        return getDoublesInNearestRow(player).size() > 0;
    }

    private int getNearestRow(Player player){
        return player.getPieceColour() == Colour.WHITE ? getSize() - 1 : 0;
    }

    private int getFurthestRow(Player player){
        return player.getPieceColour() == Colour.WHITE ? 0 : getSize() - 1;
    }

    public static GameBoard createCopy(GameBoard board){

        GameBoard toReturn = new GameBoard(board.getSize() , board.getSize());

        for (Piece piece : board.getPieces()){
            toReturn.getSpace(piece.getPosition()).setPiece(piece);
            toReturn.getSpace(piece.getPosition()).getPiece().updateLegalMoves(toReturn);
        }

        return toReturn;
    }
}
