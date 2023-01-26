package AI;

import AbstractClasses.Move;

import java.util.Collections;


public class Tree {

    private Node root;
    private int depth;

    public Tree(State state, int depth){
        this.root = new Node(state);
        this.depth = depth;

        growTree(this.root, depth);
    }

    public Node getRoot(){
        return this.root;
    }

    public void growTree(Node node, int depth){

        State state = node.getState();

        if (state.isTerminalState() || depth == 0){
            return;
        }

        for (State nextState : state.getNextStates()){
            Node n = new Node(nextState);
            node.addNode(n);

            growTree(n , depth - 1);
        }

    }


    public Move getBestMove(){
        return this.root.getChildren().get(0).getState().getBoard().getLastMovePlayed();
    }


}
