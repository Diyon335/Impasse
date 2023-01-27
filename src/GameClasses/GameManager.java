package GameClasses;

import AI.*;
import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;
import Exceptions.InvalidPiecePlacementException;
import GUI.GUI;
import Moves.*;

import java.util.*;

public class GameManager {

    private final GameBoard board;
    private Player white, black, turn;

    private boolean pieceHeld;
    private Piece pieceToMove;
    private GUI gui;
    private boolean isFirstMove;
    private int idTime;

    public GameManager(int rows, int columns, boolean p1IsAI, boolean p2IsAI, int idTime){

        this.board = new GameBoard(rows, columns);

        this.white = this.board.getWhite();
        this.black = this.board.getBlack();

        this.isFirstMove = true;
        this.pieceHeld = false;
        this.pieceToMove = null;
        this.idTime = idTime;

        if (p1IsAI){
            this.white.setAsAI();

        } else {
            this.isFirstMove = false;
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

    public boolean applyMove(Move move){

        if (move != null && move.canBeApplied()){

            System.out.println(move);
            move.movePiece(this.board);
            drawGUI();
            return true;
        }

        System.out.println("You cannot move here");
        return false;
    }

    public void drawGUI(){
        this.gui.getPanel().drawBoard();
    }

    public void registerTurn(){

        if (getTurn().isAI()){
            drawGUI();
        }

        if (getTurn().getAmountOfPieces() == 0){
            System.out.println(getTurn().getPieceColour()+" won the game!");
            return;
        }

        for (Piece p : this.board.getPieces()){
            p.updateLegalMoves(this.board);
        }

        if (this.board.hasSingleInFurthestRow(getTurn()) && getTurn().hasFreeSingles()){

            System.out.println(getTurn().getPieceColour().toString()+" should perform a crown");

            play();

            return;
        }

        if (this.board.hasDoublesInNearestRow(getTurn())){

            System.out.println(getTurn().getPieceColour().toString()+" should perform a bear off");

            if (getTurn().isAI()){
                applyMove(makeRandomMove(getTurn()));
                registerTurn();
            }

            return;
        }

        this.board.changeTurn();
        this.turn = getTurn();

        if (getTurn().isAtImpasse()){

            for (Piece piece : getTurn().getPieces()){
                piece.clearMoves();
                Space s = this.board.getSpace(piece.getRow(), piece.getCol());
                piece.addMove(new Impasse(piece,s,s));
            }

            System.out.println(getTurn().getPieceColour().toString()+" has reached an impasse, they may remove one piece");

            play();
            return;
        }

        play();
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

    private Move moveAI(){

        IterativeDeepening id = new IterativeDeepening(this.board, 1);
        id.applyID(this.idTime);
        Move m = id.getBestMove();
        Piece p = getTurn().getPieceFromCopy(m.getMovingPiece());

        return p.getMove(m.getFrom(), m.getTo());
    }


    public void play(){
        System.out.println(this.turn.getPieceColour().toString()+" to move");

        if (getTurn().isAI() && this.isFirstMove){
            Move m = makeRandomMove(getTurn());
            this.isFirstMove = false;

            applyMove(m);
            registerTurn();
            return;
        }

        if (getTurn().isAI()){
            Move m = moveAI();

            applyMove(m);
            registerTurn();
        }

    }

    public Move makeRandomMove(Player player){
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

        return move;
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
