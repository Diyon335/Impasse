package GUI;

import GameClasses.Space;

import javax.swing.*;
import java.awt.*;

public class BoardLabel extends JLabel {

    private Space space;

    public BoardLabel(Space space){
        this.space = space;
    }

    public Space getSpace() {
        return this.space;
    }
}
