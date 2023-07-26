package game.display;

import game.Board;
import game.Tile;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    private final ArrayList<Button> buttons;
    private Tile selected;
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

    public void setSelected(Tile selected) {
        this.selected = selected;
    }

    public Tile getSelected() {
        return selected;
    }
}
