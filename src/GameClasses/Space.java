package GameClasses;


import AbstractClasses.Piece;
import Enums.Colour;

import java.util.Arrays;

public class Space implements Comparable<Space> {

    private Piece piece;
    private int[] position;
    private Colour colour;

    public Space (Piece piece, int[] position, Colour colour){
        this.piece = piece;
        this.position = position;
        this.colour = colour;
    }

    public Colour getColour(){
        return this.colour;
    }

    public int[] getPosition() {
        return this.position;
    }

    public int getRow(){
        return this.position[0];
    }

    public int getCol(){
        return this.position[1];
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    @Override
    public String toString(){
        String col = Character.toString((char)getCol()+65);
        String row = Integer.toString(8 - getRow());
        return col+""+row;
    }


    @Override
    public int compareTo(Space space) {

        if (Arrays.equals(this.position, space.getPosition())){
            return 0;
        }

        return -1;
    }

}
