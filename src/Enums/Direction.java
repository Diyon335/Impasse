package Enums;

public enum Direction {

    WHITE_FORWARD_LEFT(-1, -1),
    WHITE_FORWARD_RIGHT(-1, 1),
    WHITE_BACKWARD_LEFT(1, -1),
    WHITE_BACKWARD_RIGHT(1, 1),
    BLACK_FORWARD_LEFT(1 , 1),
    BLACK_FORWARD_RIGHT(1, -1),
    BLACK_BACKWARD_LEFT(-1, 1),
    BLACK_BACKWARD_RIGHT(-1 , -1);

    private int rowChange, colChange;

    Direction(int rowChange, int colChange){
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    public int getRowChange() {
        return this.rowChange;
    }

    public int getColChange() {
        return this.colChange;
    }
}
