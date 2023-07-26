package game.display;

import game.Board;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    private final ArrayList<Button> buttons;
    private Board.Tile selected;
    public UI() {
        buttons = new ArrayList<>();
        Button pieceButton = new PieceButton((int)(0.5* Board.TILE_SIZE), (int)(0.5 * Board.TILE_SIZE),
                Board.TILE_SIZE, Board.TILE_SIZE);
        buttons.add(pieceButton);
    }

    public void render(Graphics2D gd2){
        for (Button b:buttons) {
            b.render(gd2, selected);
        }
    }

    public void setSelected(Board.Tile selected) {
        this.selected = selected;
    }

    public Board.Tile getSelected() {
        return selected;
    }
}
