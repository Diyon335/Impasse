package Exceptions;

public class InvalidPiecePlacementException extends Exception{

    private String errorMessage;

    public InvalidPiecePlacementException (String errorMessage){
        this.errorMessage = errorMessage;
    }

    /**
     * @return returns a string with the exception message
     */
    @Override
    public String toString() {
        return this.errorMessage;
    }
}
