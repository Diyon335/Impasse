package GameClasses;

import AbstractClasses.Piece;
import AbstractClasses.Move;
import GameClasses.Player;
import GameClasses.Space;
import Enums.Direction;
import Enums.Colour;
import GameClasses.GameManager;
import Moves.BearOff;
import Moves.Slide;
import Moves.Step;
import Moves.Transpose;

import java.util.ArrayList;

public class DoublePiece extends Piece {

    public DoublePiece(Colour colour, Player player, int[] position, GameManager gameManager) {
        super(colour, player, position, gameManager);
    }

    public DoublePiece(Colour pieceColour, Player player, int[] position) {
        super(pieceColour, player, position);
    }

    public boolean canBearOff(){
        return getBearOffMoves().size() > 0;
    }

    public ArrayList<Move> getBearOffMoves(){
        ArrayList<Move> toReturn = new ArrayList<>();
        for (Move move : getLegalMoves()){

            if (move instanceof BearOff){
                toReturn.add(move);
            }
        }

        return toReturn;
    }

    @Override
    public void updateLegalMoves(GameBoard board) {
        clearMoves();

        //Must bear off
        if(board.hasDoublesInNearestRow(getPlayer())){

            for (Piece piece : board.getDoublesInNearestRow(getPlayer())){

                //Space from = getGameManager().getSpaceAtIndex(piece.getPosition());
                //Space to = getGameManager().getSpaceAtIndex(piece.getPosition());

                Space from = board.getSpace(piece.getRow(), piece.getCol());
                addMove(new BearOff(this, from, from));
            }

            return;
        }


        Direction leftBack;
        Direction rightBack;

        if(isWhite()){
            leftBack = Direction.WHITE_BACKWARD_LEFT;
            rightBack = Direction.WHITE_BACKWARD_RIGHT;
        } else {
            leftBack = Direction.BLACK_BACKWARD_LEFT;
            rightBack = Direction.BLACK_BACKWARD_RIGHT;
        }

        //Space from = getGameManager().getSpaceAtIndex(getPosition());
        Space from = board.getSpace(getRow(), getCol());

        int multiplierLeft = 1;
        int[] newPositionLeft = {getPosition()[0] + multiplierLeft*leftBack.getRowChange() , getPosition()[1] + multiplierLeft*leftBack.getColChange()};

        while (board.isOnBoard(newPositionLeft)){

            Space to = board.getSpace(newPositionLeft);

            if (to.getPiece() != null){

                if ((to.getPiece().getColour() == from.getPiece().getColour()) && (multiplierLeft == 1) && (to.getPiece() instanceof Single)){
                    addMove(new Transpose(this, from, to));
                    break;
                }

                break;

            } else {
                addMove(new Slide(this, from, to));
                multiplierLeft++;
                newPositionLeft[0] = getPosition()[0] + multiplierLeft * leftBack.getRowChange();
                newPositionLeft[1] = getPosition()[1] + multiplierLeft * leftBack.getColChange();
            }
        }

        int multiplierRight = 1;
        int[] newPositionRight = {getPosition()[0] + multiplierRight*rightBack.getRowChange() , getPosition()[1] + multiplierRight*rightBack.getColChange()};

        while (board.isOnBoard(newPositionRight)){

            Space to = board.getSpace(newPositionRight);

            if (to.getPiece() != null){

                if ((to.getPiece().getColour() == from.getPiece().getColour()) && (multiplierRight == 1) && (to.getPiece() instanceof Single)){
                    addMove(new Transpose(this, from, to));
                    break;
                }

                break;

            } else {
                addMove(new Slide(this, from, to));
                multiplierRight++;
                newPositionRight[0] = getPosition()[0] + multiplierRight * rightBack.getRowChange();
                newPositionRight[1] = getPosition()[1] + multiplierRight * rightBack.getColChange();
            }
        }


    }

    @Override
    public int getCount() {
        return 2;
    }
}
