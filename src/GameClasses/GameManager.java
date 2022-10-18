package GameClasses;

import AI.State;
import AI.Tree;
import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.GameState;
import Enums.Colour;
import Exceptions.InvalidPiecePlacementException;
import GUI.GUI;
import Moves.Impasse;

import java.util.ArrayList;

public class GameManager {

    private final GameBoard board;
    private Player white, black, turn;

    private boolean pieceHeld = false;
    private Piece pieceToMove = null;
    private GUI gui;


    public GameManager(int rows, int columns){
        this.board = new GameBoard(rows, columns);

        this.white = this.board.getWhite();
        this.black = this.board.getBlack();

        initialiseBoard();

        for (Piece piece : this.board.getPieces()){
            piece.updateLegalMoves(this.board);
        }

        this.turn = getTurn();

        this.gui = new GUI("Impasse", 720, 720, this);
        this.gui.init();
    }

    public Player getTurn(){
        return this.board.getTurn();
    }

    public boolean isPieceHeld(){
        return this.pieceHeld;
    }

    public void setPieceHeld(boolean bool){
        this.pieceHeld = bool;
    }

    public Piece getPieceToMove() {
        return this.pieceToMove;
    }

    public void setPieceToMove(Piece piece){
        this.pieceToMove = piece;
    }

    public boolean turnHasPiece(Piece piece){
        return this.getBoard().getTurn().hasPiece(piece);
    }

//    public void printBoard(){
//        for (int i = 0; i <colLetters.length;i++){
//            for (int j = 0;j<colLetters.length;j++){
//                System.out.print(getSpaceAtIndex(new int[]{i,j})+" ");
//            }
//            System.out.println("");
//        }
//    }

    public void applyMove(Move move){

        if (move != null && move.canBeApplied()){

            System.out.println(move);
            move.movePiece(this);
            registerTurn();

        } else {
            System.out.println("You cannot move here");
        }
    }

    private void registerTurn(){

        this.gui.getPanel().drawBoard();

        if (getTurn().getAmountOfPieces() == 0){
            System.out.println(getTurn().getPieceColour()+" won the game!");
            return;
        }

        for (Piece p : this.board.getPieces()){
            p.updateLegalMoves(this.board);
        }

        if (this.board.hasSingleInFurthestRow(getTurn()) && getTurn().hasFreeSingles()){

            for (Piece piece : getTurn().getPieces()){

                if (piece instanceof Single){
                    if (!((Single) piece).canCrown()){
                        piece.clearMoves();
                    }
                }

                if (piece instanceof DoublePiece){
                    piece.clearMoves();
                }
            }

            System.out.println(getTurn().getPieceColour().toString()+" should perform a crown");
            return;

        }

        if (this.board.hasDoublesInNearestRow(getTurn())){

            for (Piece piece : getTurn().getPieces()){

                if (piece instanceof Single){
                    piece.clearMoves();
                }

                if (piece instanceof DoublePiece){
                    if(!((DoublePiece) piece).canBearOff()){
                        piece.clearMoves();
                    }
                }
            }

            System.out.println(getTurn().getPieceColour().toString()+" should perform a bear off");
            return;
        }

        this.board.changeTurn();

        System.out.println(getTurn().getPieceColour().toString()+" to move");

        if (getTurn().isAtImpasse()){

            for (Piece piece : getTurn().getPieces()){
                piece.clearMoves();
                Space s = this.board.getSpace(piece.getRow(), piece.getCol());
                piece.addMove(new Impasse(piece,s,s));
            }

            System.out.println(getTurn().getPieceColour().toString()+" has reached an impasse, they may remove one piece");
        }


    }

    public GameBoard getBoard() {
        return this.board;
    }

    private void initialiseBoard(){

        try{

//            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("A1"), this),this.white);
//            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("D2"), this),this.white);
//            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("E1"), this),this.white);
//            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("H2"), this),this.white);
//            this.board.addPiece(new DoublePiece(Colour.WHITE, this.white,stringToIndex("B8"), this),this.white);
//            this.board.addPiece(new DoublePiece(Colour.WHITE, this.white,stringToIndex("C7"), this),this.white);
//            this.board.addPiece(new DoublePiece(Colour.WHITE, this.white,stringToIndex("F8"), this),this.white);
//            this.board.addPiece(new DoublePiece(Colour.WHITE, this.white,stringToIndex("G7"), this),this.white);
//
//            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("A7"), this),this.black);
//            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("D8"), this),this.black);
//            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("E7"), this),this.black);
//            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("H8"), this),this.black);
//            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("B2"), this),this.black);
//            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("C1"), this),this.black);
//            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("F2"), this),this.black);
//            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("G1"), this),this.black);


            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("G7"), this), this.white);
//            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("A1"), this),this.black);
//            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("C1"), this),this.white);
            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("D2"), this), this.black);


            for (Piece piece : this.white.getPieces()){
                this.board.getSpace(piece.getRow(), piece.getCol()).setPiece(piece);
            }

            for (Piece piece : this.black.getPieces()){
                this.board.getSpace(piece.getRow(), piece.getCol()).setPiece(piece);
            }

        } catch (InvalidPiecePlacementException e){
            e.printStackTrace();
        }
    }


    public void play(){
        System.out.println(this.turn.getPieceColour().toString()+" to move");

        GameBoard boardCopy = GameBoard.createCopy(this.board);

//        System.out.println(boardCopy);
//        System.out.println(this.white == boardCopy.getWhite());
//        System.out.println(this.black == boardCopy.getBlack());
//        System.out.println(boardCopy.getPieces());
//
//        for (Piece piece : boardCopy.getPieces()){
//            System.out.println(piece.getPlayer().getPieceColour());
//        }
//        System.out.println(boardCopy.getWhite().getAmountOfPieces());
//        System.out.println(boardCopy.getBlack().getAmountOfPieces());
//
//        System.out.println(this.board == boardCopy);

        State s = new State(boardCopy, this.board.getTurn(), getOpponent());
        Tree tree = new Tree(s, 4, false);



        tree.alphaBeta(tree.getRoot(), 2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        System.out.println(tree.getBestMove());
    }

    public Player getOpponent(){
        return getTurn().getPieceColour() == Colour.WHITE ? this.black : this.white;
    }

    public int[] stringToIndex(String position) throws InvalidPiecePlacementException {

        if(position.length() != 2){
            throw new InvalidPiecePlacementException("Incorrect position format");
        }

        char[] index = position.toCharArray();
        int letter = index[0];
        int number = Integer.parseInt(String.valueOf(index[1]));

        if ( letter < 65 || letter > 72 || number < 1 || number > 8){
            throw new InvalidPiecePlacementException("Incorrect position format");
        }

        return new int[]{this.board.getSize() - number, letter - 65};
    }


}
