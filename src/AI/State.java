package AI;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import Enums.Colour;
import GameClasses.*;
import Moves.BearOff;
import Moves.Crown;
import Moves.Impasse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class State {

    private List<Move> moves;
    private GameBoard board;

    private Player player, opponent;

    public State (GameBoard board){

        this.board = board;
        this.player = board.getTurn();
        this.opponent = board.getOpponent();

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

    public Player getOpponent(){
        return this.opponent;
    }

    public int possibleNextStates(){
        return this.moves.size();
    }

    public List<State> getNextStates(){

        List<State> toReturn = new ArrayList<>();

        for (Move move : this.moves){

            if (move.canBeApplied()){

                GameBoard boardCopy = new GameBoard(this.board.getSize(), this.board.getSize());
                boardCopy.copyBoardFrom(this.board);

                Player player = boardCopy.getTurn();
                Player opponent = boardCopy.getOpponent();

                move.movePiece(boardCopy);

                for (Piece piece : boardCopy.getPieces()){
                    piece.updateLegalMoves(boardCopy);
                }

                if (boardCopy.hasSingleInFurthestRow(player) && player.hasFreeSingles()){

//                    for (Piece piece : player.getPieces()){
//
//                        if (piece instanceof Single){
//                            if (!((Single) piece).canCrown()){
//                                piece.clearMoves();
//                            }
//                        }
//
//                        if (piece instanceof DoublePiece){
//                            piece.clearMoves();
//                        }
//                    }

                    boardCopy.setTurn(player);
                    toReturn.add(new State(boardCopy));
                    continue;
                }

                if (boardCopy.hasDoublesInNearestRow(player)){

//                    for (Piece piece : player.getPieces()){
//
//                        if (piece instanceof Single){
//                            piece.clearMoves();
//                        }
//
//                        if (piece instanceof DoublePiece){
//                            if(!((DoublePiece) piece).canBearOff()){
//                                piece.clearMoves();
//                            }
//                        }
//                    }

                    boardCopy.setTurn(player);
                    toReturn.add(new State(boardCopy));
                    continue;

                }

                boardCopy.setTurn(opponent);
                toReturn.add(new State(boardCopy));
            }
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
