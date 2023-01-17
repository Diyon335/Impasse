package AI;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import GameClasses.*;
import Moves.Impasse;

import java.util.ArrayList;
import java.util.List;

public class State {

    private List<Move> moves;
    private GameBoard board;

    private Player player, opponent;

    public State (GameBoard board){

        this.board = board;
        this.player = board.getTurn();
        this.opponent = board.getOpponent();

        for (Piece p : this.board.getPieces()){
            p.updateLegalMoves(this.board);
        }

        this.moves = new ArrayList<>();

        if (this.player.isAtImpasse()){

            for (Piece piece : this.player.getPieces()){

                piece.clearMoves();
                Space s = this.board.getSpace(piece.getRow(), piece.getCol());
                Impasse i = new Impasse(piece, s , s);
                piece.addMove(i);
                this.moves.add(i);
            }

        } else {

            for (Piece piece : this.player.getPieces()){

                this.moves.addAll(piece.getLegalMoves());
            }
        }
    }

    public Player getPlayer(){
        return this.player;
    }

    public List<State> getNextStates(){

        List<State> toReturn = new ArrayList<>();

        for (Move move : this.moves){

            GameBoard boardCopy = new GameBoard(this.board.getSize(), this.board.getSize());
            boardCopy.copyBoardFrom(this.board);

            Player player = boardCopy.getTurn();

            move.movePiece(boardCopy);

            for (Piece piece : boardCopy.getPieces()){
                piece.updateLegalMoves(boardCopy);
            }

            if (boardCopy.hasSingleInFurthestRow(player) && player.hasFreeSingles()){
                toReturn.add(new State(boardCopy));
                continue;
            }

            if (boardCopy.hasDoublesInNearestRow(player)){
                toReturn.add(new State(boardCopy));
                continue;

            }

            boardCopy.changeTurn();
            toReturn.add(new State(boardCopy));
        }

        return toReturn;
    }


    public GameBoard getBoard(){
        return this.board;
    }

    public List<Move> getMoves(){
        return this.moves;
    }

    public boolean isTerminalState(){
        return this.board.getWhitePieces() == 0 || this.board.getBlackPieces() == 0;
    }


    @Override
    public String toString(){
        return this.board.toString();
    }
}
