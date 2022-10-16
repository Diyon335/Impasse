package GUI;

import GameClasses.GameManager;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private JFrame frame;
    private GameManager gameManager;
    private int width, height;
    private BoardPanel panel;

    public GUI(String title, int width, int height, GameManager gameManager){
        this.gameManager = gameManager;
        this.frame = new JFrame(title);
        this.width = width;
        this.height = height;
        this.panel = new BoardPanel(gameManager);

    }

    public BoardPanel getPanel(){
        return this.panel;
    }

    public void init(){
        this.frame.setPreferredSize(new Dimension(width,height));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.panel.setSize(new Dimension(this.width, this.height));
        this.frame.add(this.panel);
        this.frame.setContentPane(this.panel);

        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setLocationRelativeTo(null);
    }
}
