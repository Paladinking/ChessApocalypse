package game.display;

import java.awt.*;

public class Button {
    int x, y, width, height;
    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.CYAN);
        g2d.fillRect(x, y, width, height);
    }
}
