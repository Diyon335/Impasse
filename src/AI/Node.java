package AI;

import Enums.Colour;

import java.util.Collections;
import java.util.Vector;

/**
 * Class for the Node object
 */
public class Node implements Comparable<Node>{

    private State state;
    private Node parent;
    private Vector<Node> children;
    private int score;

    /**
     * Constructor for the node
     * @param state State within the node
     * @param children A vector array of nodes, each with their own state
     * @param parent The parent of the node
     */
    public Node (State state, Vector<Node> children, Node parent){
        this.state = state;
        this.children = children;
        this.parent = parent;
        this.score = 0;
    }

    /**
     * Constructor for the node
     * @param state State within the node
     */
    public Node (State state){
        this(state, new Vector<>(10), null);
    }

    /**
     * Gets the state within the node
     * @return Returns the state of the node
     */
    public State getState(){
        return this.state;
    }

    /**
     * Sets the score of the node
     * @param score Integer indicating the score of the node
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Gets the score of the node
     * @return Returns an integer indicating the score of the node
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the parent of the node
     * @param node Parent node
     */
    public void setParent(Node node){
        this.parent = node;
    }

    /**
     * Returns a boolean indicating if the state is an end state
     * @return Returns a boolean
     */
    public boolean isTerminalNode(){
        return this.state.isTerminalState();
    }

    /**
     * Gets the child states of the node
     * @return Returns a vector array of nodes
     */
    public Vector<Node> getChildren(){
        return this.children;
    }

    public void addNode(Node node){
        this.children.add(node);
        node.setParent(this);
    }

    public void sortChildren(){
        Collections.sort(this.children);
    }

    private boolean parentIsMaximiser(){
        return this.parent.getState().getPlayer().getPieceColour() == Colour.WHITE;
    }

    @Override
    public String toString(){
        return this.state.getBoard().getLastMovePlayed().toString()+"/score: "+this.score;
    }

    @Override
    public int compareTo(Node node) {

        if (node.getScore() == 0){
            return this.getScore();
        }

        if (parentIsMaximiser()){
            return node.getScore() - this.getScore();
        }

        return this.getScore() - node.getScore();
    }
}