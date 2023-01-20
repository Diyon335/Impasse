package AI;

import AbstractClasses.Move;
import Enums.Colour;
import GameClasses.GameBoard;

public class IterativeDeepening {

    private GameBoard board;
    private Tree tree;
    private int startDepth;

    public IterativeDeepening(GameBoard board, int startDepth){

        this.board = board;
        this.startDepth = startDepth;

        GameBoard boardCopy = new GameBoard(8, 8);
        boardCopy.copyBoardFrom(this.board);
        State stateCopy = new State(boardCopy);

        this.tree = new Tree(stateCopy, this.startDepth);
    }

    public void applyID(int seconds){

        long startTime = System.currentTimeMillis();
        long endTime = (long) seconds * 1000;
        long currentTime = System.currentTimeMillis();

        boolean maxPlayer = this.board.getTurn().getPieceColour() == Colour.WHITE;
        Node currentParentNode = this.tree.getRoot();

        while (currentTime - startTime < endTime){

            AlphaBeta alphaBeta = new AlphaBeta(this.tree);
            alphaBeta.applyAlphaBeta(this.tree.getRoot(), this.startDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, maxPlayer);

            if (currentParentNode == this.tree.getRoot()){

                this.tree.sortRootChildren();

            } else {

                currentParentNode.sortChildren();
            }

            int i = 0;
            while (currentParentNode.getChildren().size() > 0 && i < this.startDepth){
                currentParentNode = currentParentNode.getChildren().get(0);
                i++;
            }

            this.tree.growTree(currentParentNode, 1);

            this.startDepth++;
            currentTime = System.currentTimeMillis();
        }

        int i = 0;
        Node n = this.tree.getRoot();
        while (currentParentNode != n && n.getChildren().size() > 0){
            n = n.getChildren().get(0);
            i++;
        }
        System.out.println("Deepest depth reached: "+i);

    }

    public Move getBestMove(){
        return this.tree.getBestMove();
    }
}
