package game.display;

import game.Board;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    private final ArrayList<Button> buttons;
    private Board.Tile selected;
    public UI() {
        buttons = new ArrayList<>();
        Button peiceButton = new PeiceButton((int)(0.5* Board.TILE_SIZE), (int)(0.5 * Board.TILE_SIZE),
                2 * Board.TILE_SIZE, Board.TILE_SIZE);
        buttons.add(peiceButton);
    }

    public void render(Graphics2D gd2){
        for (Button b:buttons) {
            b.render(gd2, selected);
        }
    }

    public void setSelected(Board.Tile selected) {
        this.selected = selected;
    }
}
