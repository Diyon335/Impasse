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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameManager {

    private final GameBoard board;
    private Player white, black, turn;

    private boolean pieceHeld;
    private Piece pieceToMove;
    private GUI gui;


    public GameManager(int rows, int columns, boolean p1IsAI, boolean p2IsAI){
        this.board = new GameBoard(rows, columns);

        this.white = this.board.getWhite();
        this.black = this.board.getBlack();

        this.pieceHeld = false;
        this.pieceToMove = null;

        if (p1IsAI){
            this.white.setAsAI();
        }

        if (p2IsAI){
            this.black.setAsAI();
        }

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

//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            move.movePiece(this.board);

            registerTurn();


        } else {
            System.out.println(move);
            System.out.println(this.board);
            for (Piece p : getTurn().getPieces()){
                System.out.println(p + ", "+p.getLegalMoves());
            }
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

//            for (Piece piece : getTurn().getPieces()){
//
//                if (piece instanceof Single){
//                    if (!((Single) piece).canCrown()){
//                        piece.clearMoves();
//                    }
//                }
//
//                if (piece instanceof DoublePiece){
//                    piece.clearMoves();
//                }
//            }

            System.out.println(getTurn().getPieceColour().toString()+" should perform a crown");

            if (getTurn().isAI()){
                moveAI();
            }

            //makeRandomMove(getTurn());

            return;

        }

        if (this.board.hasDoublesInNearestRow(getTurn())){

//            for (Piece piece : getTurn().getPieces()){
//
//                if (piece instanceof Single){
//                    piece.clearMoves();
//                }
//
//                if (piece instanceof DoublePiece){
//                    if(!((DoublePiece) piece).canBearOff()){
//                        piece.clearMoves();
//                    }
//                }
//            }

            System.out.println(getTurn().getPieceColour().toString()+" should perform a bear off");

            if (getTurn().isAI()){
                moveAI();
            }
            //makeRandomMove(getTurn());

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

            if (getTurn().isAI()){
                moveAI();
            }

        } else {
            if (getTurn().isAI()){
                moveAI();
            }
        }


    }

    public GameBoard getBoard() {
        return this.board;
    }

    private void initialiseBoard(){

        try{

            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("A1"), this),this.white);
            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("D2"), this),this.white);
            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("E1"), this),this.white);
            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("H2"), this),this.white);
            this.board.addPiece(new DoublePiece(Colour.WHITE, this.white,stringToIndex("B8"), this),this.white);
            this.board.addPiece(new DoublePiece(Colour.WHITE, this.white,stringToIndex("C7"), this),this.white);
            this.board.addPiece(new DoublePiece(Colour.WHITE, this.white,stringToIndex("F8"), this),this.white);
            this.board.addPiece(new DoublePiece(Colour.WHITE, this.white,stringToIndex("G7"), this),this.white);

            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("A7"), this),this.black);
            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("D8"), this),this.black);
            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("E7"), this),this.black);
            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("H8"), this),this.black);
            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("B2"), this),this.black);
            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("C1"), this),this.black);
            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("F2"), this),this.black);
            this.board.addPiece(new DoublePiece(Colour.BLACK, this.black,stringToIndex("G1"), this),this.black);


//            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("G7"), this), this.white);
//            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("A3"), this),this.black);
//            this.board.addPiece(new Single(Colour.WHITE, this.white,stringToIndex("C1"), this),this.white);
//            this.board.addPiece(new Single(Colour.BLACK, this.black,stringToIndex("D2"), this), this.black);


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

    private void moveAI(){

        int rowsCols = this.board.getSize();
        GameBoard boardCopy = new GameBoard(rowsCols, rowsCols);
        boardCopy.copyBoardFrom(this.board);

        System.out.println(this.board);
        System.out.println(this.board.getPieces());
        System.out.println(boardCopy);
        System.out.println(this.board.getTurn().getAmountOfPieces());
        System.out.println(boardCopy.getTurn().getAmountOfPieces());

        State s = new State(boardCopy);

        for (Piece p : getTurn().getPieces()){
            System.out.println(p+": "+p.getLegalMoves());
        }
        System.out.println(s.getMoves());
        System.out.println(s.getBoard());
        Tree tree = new Tree(s, 2);

        boolean maxPlayer = this.board.getTurn().getPieceColour() == Colour.WHITE;
        tree.alphaBeta(tree.getRoot(), 2, Integer.MIN_VALUE, Integer.MAX_VALUE, maxPlayer);

        System.out.println(tree.getRoot().getChildren());

        Move m = tree.getBestMove();

        System.out.println(m);


        Piece p = getTurn().getPieceFromCopy(m.getMovingPiece());

//        System.out.println(m);
//        System.out.println(getTurn().getPieceColour());
//        System.out.println(p);
//        System.out.println(Arrays.toString(m.getMovingPiece().getPosition()));
//        System.out.println(getTurn().getPieces());
//        for (Piece piece : getTurn().getPieces()){
//            System.out.println(Arrays.toString(piece.getPosition()));
//        }


        applyMove(p.getMove(m.getFrom(), m.getTo()));
    }


    public void play(){
        System.out.println(this.turn.getPieceColour().toString()+" to move");

        if (getTurn().isAI()){
            makeRandomMove(getTurn());
        }
    }

    public void makeRandomMove(Player player){
        List<Move> allMoves = new ArrayList<>();

        for (Piece piece : player.getPieces()){
            allMoves.addAll(piece.getLegalMoves());
        }

        Move move = allMoves.get(new Random().nextInt(allMoves.size()));

        while (true){

            if (move.canBeApplied()){
                break;
            } else {
                move = allMoves.get(new Random().nextInt(allMoves.size()));
            }
        }

        applyMove(move);
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
