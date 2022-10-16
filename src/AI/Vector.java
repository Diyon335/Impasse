package AI;

/**
 * Class for the Vector data structure
 */
public class Vector {

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
