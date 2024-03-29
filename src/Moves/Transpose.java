package Moves;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import GameClasses.*;

import java.util.Arrays;

public class Transpose extends Move {

    public Transpose(Piece movingPiece, Space from, Space to) {
        super(movingPiece, from, to);
    }

    @Override
    public boolean isBasicMove() {
        return true;
    }

    @Override
    public boolean canBeApplied() {
        return getTo().getPiece() != null && getMovingPiece().hasLegalSpace(getTo())
                && (getFrom().getPiece() instanceof DoublePiece) && (getTo().getPiece() instanceof Single);
    }

    @Override
    public void movePiece(GameBoard board) {

        Piece single = board.getPieceAt(getTo());
        Piece doub = board.getPieceAt(getFrom());

        board.getSpace(getFrom().getRow(), getFrom().getCol()).setPiece(single);
        board.getSpace(getTo().getRow(), getTo().getCol()).setPiece(doub);

        single.setPosition(getFrom().getRow(), getFrom().getCol());
        doub.setPosition(getTo().getRow(), getTo().getCol());

        board.registerMove(this);
    }

    @Override
    public String toString(){
        return getMovingPiece().getColour().toString()+" transposed from "+getFrom().toString()+" to "+getTo().toString();
    }
}
