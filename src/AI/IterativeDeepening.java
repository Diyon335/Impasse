package AI;

import AbstractClasses.Move;
import Enums.Colour;
import GameClasses.GameBoard;

import java.util.Collections;
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

        Vector<Node> currentChildren = new Vector<>(this.tree.getRoot().getChildren());

        while (currentTime - startTime < endTime){

            AlphaBeta.applyAlphaBeta(this.tree.getRoot(), this.startDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, maxPlayer);

            Vector<Node> children = new Vector<>();

            sortTree(this.tree.getRoot());

            for (Node child : currentChildren){

                if (child.isTerminalNode()){
                    continue;
                }

                this.tree.growTree(child, 1);

                children.addAll(child.getChildren());

                currentTime = System.currentTimeMillis();

                if (currentTime - startTime >= endTime){
                    AlphaBeta.applyAlphaBeta(this.tree.getRoot(), this.startDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, maxPlayer);
                    sortTree(this.tree.getRoot());
                    break;
                }
            }

            currentChildren = new Vector<>(children);

            this.startDepth++;
            currentTime = System.currentTimeMillis();
        }

        System.out.println("Depth reached: "+this.startDepth);

    }

    public void sortTree(Node node){

        if (node.getChildren().size() < 1){
            return;
        }

        node.sortChildren();

        for (Node n : node.getChildren()){
            sortTree(n);
        }
    }

    public Move getBestMove(){
        return this.tree.getBestMove();
    }
}


