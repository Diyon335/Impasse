package AI;

import Enums.Colour;

public class Node implements Comparable<Node>{

    private State state;
    private Node parent;
    private Vector children;
    private int score;

    public Node (State state, Vector children, Node parent){
        this.state = state;
        this.children = children;
        this.parent = parent;
        this.score = 0;
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

    public void addNode(Node node, Colour playerColour, boolean orderMoves){

        if (this.children.isEmpty()){
            addChildLast(node);
            return;
        }

        if (orderMoves){

            int nodeScore = node.getState().evaluateState();
            int lastChildScore = this.children.getLast().getState().evaluateState();

            if (nodeScore > lastChildScore){

                if (playerColour == Colour.WHITE){
                    addChildLast(node);
                    return;
                }

                this.children.addBeforeLast(node);

            } else {

                if (playerColour == Colour.WHITE){
                    this.children.addBeforeLast(node);
                    return;
                }

                addChildLast(node);
            }

            return;
        }

        addChildLast(node);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Node o) {
        return this.state.compareTo(o.getState());
    }

    @Override
    public String toString(){
        return this.state.toString();
    }
}
