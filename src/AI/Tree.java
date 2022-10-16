package AI;

import Enums.Colour;

public class Tree {

    private Node root;

    public Tree(State state, int depth, Colour playerColour, boolean ordered){
        this.root = new Node(state);

        growTree(this.root, depth, playerColour, ordered);
    }

    private void growTree(Node node, int depth, Colour playerColour, boolean ordered){

        int i = 0;

        State state = node.getState();

        for (State nextState : state.getNextStates()){
            node.addNode(new Node(nextState), playerColour, ordered);
        }

        while (i < depth){

            for (int j = 0; j < node.getChildren().size(); j++){
                Node child = node.getChildren().get(j);
                child.setParent(node);

                growTree(child, depth - 1, playerColour, ordered);
            }

            i++;
        }

    }
}
