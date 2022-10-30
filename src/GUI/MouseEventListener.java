package GUI;

import AbstractClasses.Piece;
import GameClasses.GameManager;
import GameClasses.Space;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventListener extends MouseAdapter {

    private GameManager gameManager;

    public MouseEventListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    @Override
    public void mousePressed(MouseEvent e){
        if(getGameManager().isPieceHeld()){

            Space to = ((BoardLabel) e.getSource()).getSpace();
            Piece piece = getGameManager().getPieceToMove();
            Space from = getGameManager().getBoard().getSpace(piece.getPosition());

            if ( getGameManager().applyMove(piece.getMove(from,to)) ){
                getGameManager().registerTurn();
            }

            getGameManager().setPieceHeld(false);
            getGameManager().setPieceToMove(null);

        } else {

            Space from = ((BoardLabel) e.getSource()).getSpace();

            if (from.getPiece() == null){
                System.out.println("No piece here");
                return;
            }

            if (!getGameManager().turnHasPiece(from.getPiece())){
                System.out.println("You do not own this piece");
                return;
            }

            getGameManager().setPieceToMove(from.getPiece());
            getGameManager().setPieceHeld(true);
        }
    }
}
