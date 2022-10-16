package GUI;

import Enums.Colour;
import GameClasses.GameManager;
import GameClasses.Space;
import GameClasses.DoublePiece;
import GameClasses.Single;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private BoardLabel[][] label;
    private GameManager gameManager;

    public BoardPanel(GameManager gameManager){
        this.gameManager = gameManager;

        GridLayout gridLayout = new GridLayout(gameManager.getBoard().getSize(), gameManager.getBoard().getSize());
        setLayout(gridLayout);

        this.label = new BoardLabel[gameManager.getBoard().getSize()][gameManager.getBoard().getSize()];

        for (int i = 0; i < gameManager.getBoard().getSize(); i++){
            for (int j = 0; j < gameManager.getBoard().getSize(); j++){
                this.label[i][j] = new BoardLabel(gameManager.getBoard().getSpace(i,j));
                this.label[i][j].addMouseListener(new MouseEventListener(this.gameManager));

                add(this.label[i][j]);
            }
        }

        drawBoard();
    }

    public void drawBoard(){

        for (int i = 0; i < this.gameManager.getBoard().getSize(); i++){
            for (int j = 0; j < this.gameManager.getBoard().getSize(); j++){

                Space space = this.gameManager.getBoard().getSpace(i,j);

                if (space.getPiece() == null){

                    if (space.getColour() == Colour.WHITE){
                        this.label[i][j].setIcon(new ImageIcon("white_space.png"));
                    } else {
                        this.label[i][j].setIcon(new ImageIcon("black_space.png"));
                    }
                    continue;
                }

                if (space.getPiece() instanceof Single){

                    if (space.getPiece().getColour() == Colour.WHITE){
                        this.label[i][j].setIcon(new ImageIcon("white_single.png"));

                    } else {
                        this.label[i][j].setIcon(new ImageIcon("black_single.png"));
                    }
                    continue;
                }

                if (space.getPiece() instanceof DoublePiece){

                    if (space.getPiece().getColour() == Colour.WHITE){
                        this.label[i][j].setIcon(new ImageIcon("white_double.png"));

                    } else {
                        this.label[i][j].setIcon(new ImageIcon("black_double.png"));
                    }
                }
            }
        }

        repaint();
    }
}
