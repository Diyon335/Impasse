package AI;

public class EvaluationFunction {


    public static int evaluate(State state){

        if (state.isTerminalState()){
            return Integer.MAX_VALUE;
        }

        int pieceDiff = 0;

        if (state.getBoard().getWhitePieces() < state.getBoard().getBlackPieces()){
            pieceDiff+=2;
        }


        return pieceDiff;
    }
}
