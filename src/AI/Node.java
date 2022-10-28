package AI;

import Enums.Colour;

public class Node {

    private State state;
    private Node parent;
    private Vector children;
    private int score;
    private Node bestChild;

    public Node (State state, Vector children, Node parent){
        this.state = state;
        this.children = children;
        this.parent = parent;
        this.score = 0;
        this.bestChild = null;
    }

    public Node (State state){
        this(state, new Vector(10), null);
    }

    public State getState(){
        return this.state;
    }

    public Node getParent(){
        return this.parent;
    }

    public void setBestChild(Node child){
        this.bestChild = child;
    }

    public Node getBestChild(){
        return this.bestChild;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setParent(Node node){
        this.parent = node;
    }

    public boolean isTerminalNode(){
        return this.state.isTerminalState();
    }

    public Vector getChildren(){
        return this.children;
    }

    private void addChildFirst(Node child){
        this.children.addFirst(child);
    }

    private void addChildLast(Node child){this.children.addLast(child);}

    public void addNode(Node node){

//        if (this.children.isEmpty()){
//            addChildLast(node);
//            return;
//        }
//
//        if (orderMoves){
//
//            int nodeScore = node.getState().evaluateState();
//            int lastChildScore = this.children.getLast().getState().evaluateState();
//
//            node.setScore(nodeScore);
//            this.children.getLast().setScore(lastChildScore);
//
//            if (nodeScore > lastChildScore){
//
//                if (playerColour == Colour.WHITE){
//                    addChildLast(node);
//                    return;
//                }
//
//                this.children.addBeforeLast(node);
//                return;
//
//            } else {
//
//                if (playerColour == Colour.WHITE){
//                    this.children.addBeforeLast(node);
//                    return;
//                }
//
//                addChildLast(node);
//                return;
//            }
//
//        }

        addChildLast(node);
        node.setParent(this);
    }

    @Override
    public String toString(){
        return this.state.getBoard().getLastMovePlayed().toString()+"/score: "+this.score;
    }
}
