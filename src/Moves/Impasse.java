package Moves;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import GameClasses.*;

public class Impasse extends Move {

    public Impasse(Piece movingPiece, Space from, Space to) {
        super(movingPiece, from, to);
    }

    @Override
    public boolean isBasicMove() {
        return false;
    }

    @Override
    public boolean canBeApplied() {
        return getTo().getPiece()!=null && getMovingPiece().hasLegalSpace(getTo())
                && (
                ((getFrom().getPiece() instanceof DoublePiece) && (getTo().getPiece() instanceof DoublePiece)) ||
                ((getFrom().getPiece() instanceof Single) && (getTo().getPiece() instanceof Single))
        );
    }

    @Override
    public void movePiece(GameBoard board) {

        Player player = getMovingPiece().getPlayer();
        Piece piece = board.getPieceAt(getFrom());
        Space to = board.getSpace(getTo().getRow(), getTo().getCol());
        Space from = board.getSpace(getFrom().getRow(), getFrom().getCol());

        if (piece instanceof DoublePiece){
            Single newSingle = new Single(player.getPieceColour(), player, new int[]{to.getRow(), to.getCol()});

            board.removePiece(getMovingPiece(), player);
            board.addPiece(newSingle, player);

            to.setPiece(newSingle);
            board.registerMove(this);
            return;
        }

        board.removePiece(from.getPiece(), player);
        to.setPiece(null);

        board.registerMove(this);
    }


    @Override
    public String toString(){
        return getMovingPiece().getColour().toString()+" was at an impasse and removed a piece at "+getFrom().toString();
    }
}
