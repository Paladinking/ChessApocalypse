package game.display;

import game.Board;
import game.Tile;

import java.awt.*;

public class PieceButton extends Button{
    public PieceButton(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Graphics2D g2d, Tile selected) {
        super.render(g2d, selected);
        if(selected != null) {
            if (selected.hasPiece()) {
                //Ska rita pjäs

            }
        }
    }
}
