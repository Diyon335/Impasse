package GameClasses;

import AI.State;
import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;
import Moves.Impasse;

import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard implements Comparable<GameBoard>{

    private Space[][] board;
    private int rows, cols;
    private ArrayList<Piece> pieces;
    private int whitePieces, blackPieces;
    private ArrayList<Move> movesPlayed;
    private Player white, black, turn;

    public GameBoard(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.board = new Space[rows][cols];
        this.pieces = new ArrayList<>();
        this.movesPlayed = new ArrayList<>();
        this.white = new Player(Colour.WHITE);
        this.black = new Player(Colour.BLACK);
        this.turn = this.white;
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

    public void changeTurn(){
        this.turn = this.turn.getPieceColour() == Colour.WHITE ? this.black : this.white;
    }

    public void setTurn(Player player){this.turn = player;}

    public Player getTurn(){
        return this.turn;
    }

    public Player getOpponent(){
        return getTurn().getPieceColour() == Colour.WHITE ? this.black : this.white;
    }

    public Player getWhite(){
        return this.white;
    }

    public Player getBlack(){
        return this.black;
    }

    public void setWhite(Player player){
        this.white = player;
    }

    public void setBlack(Player player){
        this.black = player;
    }

    public Move getLastMovePlayed(){
        return this.movesPlayed.get(this.movesPlayed.size()-1);
    }

    public void registerMove(Move move){
        this.movesPlayed.add(move);
    }

    public int getWhitePieces(){
        return this.whitePieces;
    }

    public int getBlackPieces(){
        return this.blackPieces;
    }

    public void addPiece(Piece piece, Player player){

        player.addPiece(piece);

        if (piece.getColour() == Colour.WHITE){
            this.whitePieces+=piece.getCount();
        } else {
            this.blackPieces+=piece.getCount();
        }

        this.pieces.add(piece);

    }


    public void removePiece(Piece piece, Player player){

        player.removePiece(piece);

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

        int row = getFurthestRow(player);
        for (int col = 0; col < getSize(); col++){
            Piece piece = getSpace(row, col).getPiece();

            if (piece == null){
                continue;
            }

            if ((piece instanceof Single) && player.hasPiece(piece)){
                singles.add(piece);
            }
        }

        return singles;
    }

    public ArrayList<Piece> getFreeSingles(Player player){

        ArrayList<Piece> singles = new ArrayList<>();

        for (Piece p : player.getPieces()){
            if (p instanceof Single){
                singles.add(p);
            }
        }

        return singles;
    }

    public boolean hasSingleInFurthestRow(Player player){
        return getSinglesInFurthestRow(player).size() > 0;
    }

    public ArrayList<Piece> getDoublesInNearestRow(Player player){

        ArrayList<Piece> doubles = new ArrayList<>();

        int row = getNearestRow(player);
        for (int col = 0; col < getSize(); col++){
            Piece piece = getSpace(row, col).getPiece();

            if (piece == null){
                continue;
            }

            if ((piece instanceof DoublePiece) && player.hasPiece(piece)){
                doubles.add(piece);
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

    public void copyBoardFrom(GameBoard board){

        for (int i = 0; i< getSize(); i++){
            for (int j = 0; j < getSize(); j++){

                Piece piece = board.getSpace(i,j).getPiece();

                if (piece == null){
                    continue;
                }

                Player player = piece.getColour() == Colour.WHITE ? this.white : this.black;

                if (piece instanceof Single){

                    Single single = new Single(piece.getColour(), player, new int[]{i, j});

                    getSpace(i, j).setPiece(single);
                    single.setPosition(i, j);
                    addPiece(single, player);
                }

                if (piece instanceof DoublePiece){
                    DoublePiece d = new DoublePiece(piece.getColour(), player, new int[]{i, j});

                    getSpace(i, j).setPiece(d);
                    d.setPosition(i, j);
                    addPiece(d, player);
                }
            }
        }

        Player p = board.getTurn().getPieceColour() == Colour.WHITE ? this.white : this.black;
        setTurn(p);
    }

    public void makeStateDesirable(){
        this.whitePieces = 0;
        this.blackPieces = 0;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("");

        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.cols; j++){

                if (this.board[i][j].getPiece() == null){
                    s.append("* ");
                } else {
                    s.append(this.board[i][j].getPiece()).append(" ");
                }
            }

            s.append("\n");
        }

        return s.toString();
    }

    @Override
    public int compareTo(GameBoard gameBoard) {

        for (int i = 0; i < this.rows ; i++){
            for (int j = 0; j<this.cols; j++){

                Piece p1 = getSpace(i,j).getPiece();
                Piece p2 = gameBoard.getSpace(i,j).getPiece();

                if (p1 == null && p2 == null){
                    continue;
                }

                assert p1 != null;
                assert p2 != null;
                if (p1.compareTo(p2) == 0){
                    continue;
                }

                return -1;
            }
        }

        return 0;
    }
}
