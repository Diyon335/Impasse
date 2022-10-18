package AI;

import AbstractClasses.Move;


public class Tree {

    private Node root;
    private int depth;
    private boolean ordered;

    public Tree(State state, int depth, boolean ordered){
        this.root = new Node(state);
        this.depth = depth;
        this.ordered = ordered;


        growTree(this.root, depth, ordered);
    }

    public Node getRoot(){
        return this.root;
    }

    public int alphaBeta(Node node, int depth, int alpha, int beta, boolean maxPlayer) {

        if (node.isTerminalNode() || depth == 0){
            int value = EvaluationFunction.evaluate(node.getState());
            node.setScore(value);
            return value;
        }


        if (maxPlayer){

            int value = Integer.MIN_VALUE;

            for (int i = 0; i < node.getChildren().size(); i++){

                if (node.getChildren().get(i) == null){
                    continue;
                }

                value = Math.max(value, alphaBeta(node.getChildren().get(i), depth - 1, alpha, beta, false));

                if (value >= beta){
                    break;
                }

                alpha = Math.max(alpha, value);
            }

            node.setScore(value);
            return value;

        } else {

            int value = Integer.MAX_VALUE;

            for (int i = 0; i < node.getChildren().size(); i++){

                if (node.getChildren().get(i) == null){
                    continue;
                }

                value = Math.min(value, alphaBeta(node.getChildren().get(i), depth - 1, alpha, beta, true));

                if (value <= alpha){
                    break;
                }

                beta = Math.min(beta, value);
            }

            node.setScore(value);
            return value;
        }

    }

    public Move getBestMove(){

        Node n = this.root.getChildren().get(0);

        for (int i = 1; i < this.root.getChildren().size(); i++){
            Node child = this.root.getChildren().get(i);

            if (child == null){
                continue;
            }

            if (child.getScore() > n.getScore()){
                n = child;
            }
        }

        return n.getState().getBoard().getLastMovePlayed();
    }

    private void growTree(Node node, int depth, boolean ordered){

        State state = node.getState();

        if (state.isTerminalState() || depth == 0 || state.possibleNextStates() == 0){
            return;
        }

        for (State nextState : state.getNextStates()){
            Node n = new Node(nextState);
            node.addNode(n, nextState.getPlayer().getPieceColour(), ordered);
            n.setParent(node);

            growTree(n , depth - 1, ordered);
        }

    }

    @Override
    public String toString(){
        return this.root.getChildren().get(1).getChildren().get(0).toString();
    }
}
