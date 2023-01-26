package AI;

import AbstractClasses.Move;
import Enums.Colour;
import GameClasses.GameBoard;

import java.util.Vector;

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

        while (currentTime - startTime < endTime){

            AlphaBeta.applyAlphaBeta(this.tree.getRoot(), this.startDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, maxPlayer);

            Vector<Node> childrenAtCurrentDepth = new Vector<>();
            getChildrenAtDepth(childrenAtCurrentDepth, this.tree.getRoot(), this.startDepth);

            for (Node child : childrenAtCurrentDepth){

                this.tree.growTree(child, 1);

                currentTime = System.currentTimeMillis();

                if (currentTime - startTime < endTime){

                    sortTree(this.tree.getRoot());
                    break;
                }
            }

            this.startDepth++;
            currentTime = System.currentTimeMillis();
        }

    }

    public void sortTree(Node node){

        if (node.getScore() != 0){
            node.sortChildren();
        }

        for (Node n : node.getChildren()){
            sortTree(n);
        }
    }

    public void getChildrenAtDepth(Vector<Node> childrenToAdd, Node startNode, int depth){

        if (depth - 1 == 0){

            for (Node child: startNode.getChildren()){

                if (child.getScore() != 0){

                    childrenToAdd.add(child);
                }
            }

            return;
        }

        for (Node child : startNode.getChildren()){

            getChildrenAtDepth(childrenToAdd, child, depth - 1);
        }
    }

    public Move getBestMove(){
        return this.tree.getBestMove();
    }
}
