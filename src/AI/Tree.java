package AI;

import AbstractClasses.Move;
import Enums.Colour;
import GameClasses.Player;

import java.util.List;

public class Tree {

    /**
     * Class for the Vector data structure
     */
    private class Vector {

        private Node[] data;
        private int count;

        /**
         * Constructor for the Vector data structure
         * @param capacity The initial capacity of the vector
         */
        public Vector(int capacity) {
            this.data =  new Node[capacity];
            this.count = 0;
        }

        /**
         * Returns a Node at a specified index
         * @param index Integer indicating the index
         * @return Returns a node
         */
        public Node get(int index){return this.data[index];}

        /**
         * Gets the first Node
         * @return Returns a node
         */
        public Node getFirst(){return this.data[0];}

        /**
         * Gets the last Node
         * @return Returns a node
         */
        public Node getLast(){return this.data[this.count - 1];}


        /**
         * Adds a Node to the beginning of the vector
         * @param node Node to be added
         */
        public void addFirst(Node node){
            //check if its about to be full
            if(isFull()){
                extendCapacity();
            }

            for (int i = this.count; i > 0; i--){
                this.data[i] = this.data[i-1];
            }

            this.data[0] = node;
            count++;
        }

        /**
         * Adds a Node to the last position of the vector
         * @param node Node to be added
         */
        public void addLast(Node node){
            if(isFull()){
                extendCapacity();
            }

            this.data[this.count] = node;
            this.count++;
        }

        /**
         * Sets an index of the vector to be a Node
         * @param index Integer indicating index
         * @param node Node to be set
         */
        public void set(int index, Node node){
            this.data[index] = node;
        }

        /**
         * Extends the capacity of the vector by 10
         */
        private void extendCapacity(){
            Node[] newNodes = new Node[this.count + 10];

            for (int i = 0; i < this.data.length; i++){
                newNodes[i] = data[i];
            }

            this.data = newNodes;
        }

        public void addBeforeLast(Node node){
            if (isFull()){
                extendCapacity();
            }

            this.data[this.count] = getLast();
            this.data[this.count-1] = node;
            this.count++;
        }

        /**
         * Checks if the vector is empty
         * @return Returns a boolean
         */
        public boolean isEmpty(){
            return size()==0;
        }


        /**
         * Checks if the vector is full
         * @return Returns a boolean
         */
        private boolean isFull(){
            return this.count + 1 >= this.data.length;
        }

        /**
         * Returns the size of the vector
         * @return Returns an integer
         */
        public int size(){
            return this.count;
        }
    }

    private class Node implements Comparable<Node>{

        private State state;
        private Node parent;
        private Vector children;

        public Node (State state, Vector children, Node parent){
            this.state = state;
            this.children = children;
            this.parent = parent;
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
         *
         * <p>The implementor must ensure {@link Integer#signum
         * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * x.compareTo(y)} must throw an exception if and only if {@code
         * y.compareTo(x)} throws an exception.)
         *
         * <p>The implementor must also ensure that the relation is transitive:
         * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
         * {@code x.compareTo(z) > 0}.
         *
         * <p>Finally, the implementor must ensure that {@code
         * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
         * == signum(y.compareTo(z))}, for all {@code z}.
         *
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         * @apiNote It is strongly recommended, but <i>not</i> strictly required that
         * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
         * class that implements the {@code Comparable} interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         */
        @Override
        public int compareTo(Node o) {
            return this.state.compareTo(o.getState());
        }
    }

    private Node root;

    public Tree(State state){
        this.root = new Node(state);
    }

    public Node getRoot(){
        return this.root;
    }

    public void growTree(){

        Node node = this.root;


    }
}
