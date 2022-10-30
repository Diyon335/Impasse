package Moves;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import GameClasses.*;

import java.util.Arrays;

public class BearOff extends Move {

    public BearOff(Piece movingPiece, Space from, Space to) {
        super(movingPiece, from, to);
    }


    @Override
    public boolean isBasicMove() {
        return false;
    }

    @Override
    public boolean canBeApplied() {
        return getTo().getPiece()!=null && getMovingPiece().hasLegalSpace(getTo())
                && (getFrom().getPiece() instanceof DoublePiece) && (getTo().getPiece() instanceof DoublePiece);
    }

    @Override
    public void movePiece(GameManager gameManager) {

        Player player = gameManager.getTurn();

        Single newSingle = new Single(player.getPieceColour(), player, new int[]{getTo().getRow(),getTo().getCol()}, gameManager);

        gameManager.getBoard().removePiece(getMovingPiece(), player);
        gameManager.getBoard().addPiece(newSingle, player);

        getTo().setPiece(newSingle);
        getTo().getPiece().setPosition(getTo().getRow(), getTo().getCol());
    }

    @Override
    public void movePiece(GameBoard board) {

        Player player = getMovingPiece().getPlayer();

        Single newSingle = new Single(player.getPieceColour(), player, new int[]{getTo().getRow(),getTo().getCol()});

        board.removePiece(getMovingPiece(), player);
        board.addPiece(newSingle, player);

        board.getSpace(getTo().getRow(), getTo().getCol()).setPiece(newSingle);
        newSingle.setPosition(getTo().getRow(), getTo().getCol());

        board.registerMove(this);
    }


    @Override
    public String toString(){
        return getMovingPiece().getColour().toString()+" bored off a single at "+getTo().toString();
    }
}
