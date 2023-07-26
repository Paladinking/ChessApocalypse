package game.display;

import game.Board;

import java.awt.*;

public class Button {
    int x, y, width, height;
    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics2D g2d, Board.Tile selected) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x + 3, y + 3, width - 6, height -6);
    }
}
