package Moves;

import AbstractClasses.Move;
import AbstractClasses.Piece;
import GameClasses.GameBoard;
import GameClasses.GameManager;
import GameClasses.Single;
import GameClasses.Space;

import java.util.Arrays;

public class Step extends Move {


    public Step(Piece movingPiece, Space from, Space to) {
        super(movingPiece, from, to);
    }

    @Override
    public boolean isBasicMove() {
        return true;
    }

    @Override
    public boolean canBeApplied() {
        return getTo().getPiece()==null && getMovingPiece().hasLegalSpace(getTo()) && (getMovingPiece() instanceof Single);
    }

    @Override
    public void movePiece(GameManager gameManager) {

        gameManager.getBoard().getSpace(getFrom().getRow(), getFrom().getCol()).setPiece(null);
        gameManager.getBoard().getSpace(getTo().getRow(), getTo().getCol()).setPiece(getMovingPiece());
        getMovingPiece().setPosition(getTo().getRow(), getTo().getCol());

    }

    @Override
    public void movePiece(GameBoard board) {
        board.getSpace(getFrom().getRow(), getFrom().getCol()).getPiece().setPosition(getTo().getRow(), getTo().getCol());
        board.getSpace(getFrom().getRow(), getFrom().getCol()).setPiece(null);
        board.getSpace(getTo().getRow(), getTo().getCol()).setPiece(getMovingPiece());

        board.registerMove(this);
    }
}
