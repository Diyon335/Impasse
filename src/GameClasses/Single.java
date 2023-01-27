package GameClasses;

import AbstractClasses.Piece;
import AbstractClasses.Move;
import Enums.Direction;
import Enums.Colour;
import Moves.Crown;
import Moves.Slide;
import Moves.Transpose;

import java.util.ArrayList;

public class Single extends Piece {

    public Single(Colour colour, Player player, int[] position, GameManager gameManager) {
        super(colour, player, position, gameManager);
    }

    public Single(Colour pieceColour, Player player, int[] position) {
        super(pieceColour, player, position);
    }

    public boolean canCrown(){
        return getCrownMoves().size() > 0;
    }

    public ArrayList<Move> getCrownMoves(){
        ArrayList<Move> toReturn = new ArrayList<>();

        for (Move move : getLegalMoves()){

            if (move instanceof Crown){
                toReturn.add(move);
            }
        }

        return toReturn;
    }


    @Override
    public void updateLegalMoves(GameBoard board) {

        clearMoves();

        if (board.hasDoublesInNearestRow(getPlayer())){
            return;
        }

        //Must crown
        if (board.hasSingleInFurthestRow(getPlayer()) && getPlayer().hasFreeSingles()){



            for (Piece piece : board.getSinglesInFurthestRow(getPlayer())){

                if (piece.compareTo(this) == 0){
                    continue;
                }

                Space from = board.getSpace(getPosition());
                Space to = board.getSpace(piece.getPosition());
                addMove(new Crown(this, from , to));
            }

            return;
        }


        Direction leftForward;
        Direction rightForward;

        if(isWhite()){
            leftForward = Direction.WHITE_FORWARD_LEFT;
            rightForward = Direction.WHITE_FORWARD_RIGHT;
        } else {
            leftForward = Direction.BLACK_FORWARD_LEFT;
            rightForward = Direction.BLACK_FORWARD_RIGHT;
        }

        Space from = board.getSpace(getPosition());

        int multiplierLeft = 1;
        int[] newPositionLeft = {getPosition()[0] + multiplierLeft*leftForward.getRowChange() , getPosition()[1] + multiplierLeft*leftForward.getColChange()};

        while (board.isOnBoard(newPositionLeft)){

            Space to = board.getSpace(newPositionLeft);

            if (to.getPiece() != null){

                break;

            } else {
                addMove(new Slide(this, from, to));
                multiplierLeft++;
                newPositionLeft[0] = getPosition()[0] + multiplierLeft * leftForward.getRowChange();
                newPositionLeft[1] = getPosition()[1] + multiplierLeft * leftForward.getColChange();
            }
        }

        int multiplierRight = 1;
        int[] newPositionRight = {getPosition()[0] + multiplierRight*rightForward.getRowChange() , getPosition()[1] + multiplierRight*rightForward.getColChange()};

        while (board.isOnBoard(newPositionRight)){

            Space to = board.getSpace(newPositionRight);

            if (to.getPiece() != null){

                break;

            } else {
                addMove(new Slide(this, from, to));
                multiplierRight++;
                newPositionRight[0] = getPosition()[0] + multiplierRight * rightForward.getRowChange();
                newPositionRight[1] = getPosition()[1] + multiplierRight * rightForward.getColChange();
            }
        }

    }

    @Override
    public int getCount() {
        return 1;
    }


    @Override
    public String toString(){
        return this.getColour() == Colour.WHITE ? "ws" : "bs";
    }
}
