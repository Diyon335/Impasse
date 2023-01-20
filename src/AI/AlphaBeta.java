package AI;

import Enums.Colour;

public class AlphaBeta {

    private Tree tree;

    public AlphaBeta(Tree tree){
        this.tree = tree;
    }

    public int applyAlphaBeta(Node node, int depth, int alpha, int beta, boolean maxPlayer) {

        if (node.isTerminalNode() || depth == 0){
            int value = EvaluationFunction.evaluate(node.getState(), maxPlayer);
            node.setScore(value);
            return value;
        }

        if (maxPlayer){

            int value = Integer.MIN_VALUE;

            for (Node child : node.getChildren()){

                if (child == null){
                    continue;
                }

                boolean trulyMaxPlayer = child.getState().getPlayer().getPieceColour() == Colour.WHITE;

                value = Math.max(value, applyAlphaBeta(child, depth - 1, alpha, beta, trulyMaxPlayer));

                alpha = Math.max(alpha, value);

                if (alpha >= beta){
                    break;
                }

                node.setScore(alpha);
            }

            return value;

        } else {

            int value = Integer.MAX_VALUE;

            for (Node child : node.getChildren()){

                if (child == null){
                    continue;
                }

                boolean trulyMaxPlayer = child.getState().getPlayer().getPieceColour() == Colour.WHITE;

                value = Math.min(value, applyAlphaBeta(child, depth - 1, alpha, beta, trulyMaxPlayer));

                beta = Math.min(beta, value);

                if (beta <= alpha){
                    break;
                }

                node.setScore(beta);
            }

            return value;
        }

    }
}
