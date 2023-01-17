package Moves;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import Exceptions.InvalidPiecePlacementException;
import GameClasses.*;

public class Crown extends Move {

    public Crown(Piece movingPiece, Space from, Space to) {
        super(movingPiece, from, to);
    }

    @Override
    public boolean isBasicMove() {
        return false;
    }

    @Override
    public boolean canBeApplied() {
        return getTo().getPiece()!=null && getMovingPiece().hasLegalSpace(getTo())
                && (getFrom().getPiece() instanceof Single) && (getTo().getPiece() instanceof Single)
                && !getFrom().getPiece().equals(getTo().getPiece());
    }

    @Override
    public void movePiece(GameBoard board) {

        Player player = getMovingPiece().getPlayer();

        DoublePiece newDouble = new DoublePiece(player.getPieceColour(), player, new int[]{getTo().getRow(), getTo().getCol()});

        board.removePiece(getFrom().getPiece(), player);
        board.removePiece(getTo().getPiece(), player);
        board.addPiece(newDouble, player);

        board.getSpace(getFrom().getRow(), getFrom().getCol()).setPiece(null);
        board.getSpace(getTo().getRow(), getTo().getCol()).setPiece(newDouble);

        board.registerMove(this);
    }


    @Override
    public String toString(){
        return getMovingPiece().getColour().toString()+" crowned a single at "+getTo().toString()+" from "+getFrom().toString();
    }
}
