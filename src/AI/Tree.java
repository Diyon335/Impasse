package AI;

import AbstractClasses.Move;
import Enums.Colour;
import GameClasses.Player;


public class Tree {

    private Node root;
    private int depth;

    public Tree(State state, int depth, boolean orderMoves){
        this.root = new Node(state);
        this.depth = depth;

        growTree(this.root, depth, orderMoves);
    }

    public Node getRoot(){
        return this.root;
    }

    public int alphaBeta(Node node, int depth, int alpha, int beta, boolean maxPlayer) {

        if (node.isTerminalNode() || depth == 0 || node.getState().possibleNextStates() == 0){
            int value = EvaluationFunction.evaluate(node.getState(), maxPlayer);
            node.setScore(value);
            return value;
        }


        if (maxPlayer){

            int value = Integer.MIN_VALUE;

            for (int i = 0; i < node.getChildren().size(); i++){

                Node child = node.getChildren().get(i);

                if (child == null){
                    continue;
                }

                boolean trulyMaxPlayer = child.getState().getPlayer().getPieceColour() == Colour.WHITE;

                value = Math.max(value, alphaBeta(child, depth - 1, alpha, beta, trulyMaxPlayer));

                alpha = Math.max(alpha, value);

                if (alpha >= beta){
                    break;
                }

                node.setScore(value);
            }

            return value;

        } else {

            int value = Integer.MAX_VALUE;

            for (int i = 0; i < node.getChildren().size(); i++){

                Node child = node.getChildren().get(i);

                if (child == null){
                    continue;
                }

                boolean trulyMaxPlayer = child.getState().getPlayer().getPieceColour() == Colour.WHITE;

                value = Math.min(value, alphaBeta(child, depth - 1, alpha, beta, trulyMaxPlayer));

                beta = Math.min(beta, value);

                if (beta <= alpha){

                    break;
                }


                node.setScore(value);
            }

            return value;
        }

    }

    public Move getBestMove(){

        Node n = this.root.getChildren().get(0);
        Player player = this.root.getState().getPlayer();

        for (int i = 0; i < this.root.getChildren().size(); i++){

            Node child = this.root.getChildren().get(i);

            if (child == null){
                break;
            }

            if (player.getPieceColour() == Colour.WHITE){

                if (child.getScore() > n.getScore()){
                    n = child;
                }
            } else {

                if (child.getScore() < n.getScore()){
                    n = child;
                }
            }

        }

        return n.getState().getBoard().getLastMovePlayed();
    }

    private void growTree(Node node, int depth, boolean orderMoves){

        State state = node.getState();

        if (state.isTerminalState() || depth == 0){
            return;
        }

        for (State nextState : state.getNextStates()){
            Node n = new Node(nextState);
            node.addNode(n, orderMoves);

            growTree(n , depth - 1, orderMoves);
        }

    }

}
