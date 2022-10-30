package GameClasses;

import AbstractClasses.Piece;
import AbstractClasses.Move;
import GameClasses.Player;
import GameClasses.Space;
import Enums.Direction;
import Enums.Colour;
import Moves.Crown;
import Moves.Step;

import java.util.ArrayList;
import java.util.Arrays;

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

        int[] newPositionLeft = {getPosition()[0] + leftForward.getRowChange() , getPosition()[1] + leftForward.getColChange()};

        int[] newPositionRight = {getPosition()[0] + rightForward.getRowChange() , getPosition()[1] + rightForward.getColChange()};

        if (board.isOnBoard(newPositionLeft) && board.getSpace(newPositionLeft).getPiece() == null){
            addMove(new Step(this, from, board.getSpace(newPositionLeft)));
        }

        if (board.isOnBoard(newPositionRight) && board.getSpace(newPositionRight).getPiece() == null){
            addMove(new Step(this, from, board.getSpace(newPositionRight)));
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
