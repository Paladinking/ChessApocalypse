package game;

import java.awt.*;

public class Game {


    public static final Dimension SIZE = new Dimension(1920, 1080);

    private Board board;

    /**
     * Initializes the game.
     */
    void init() {

    }

    /**
     * Main logic tick method.
     * @param delta time since last call to tick
     */
    void tick(long delta) {
        double secs = delta / 1000000000.0;
    }

    /**
     * Renders one frame of the game
     * @param g2d the graphics context.
     */
    void render(Graphics2D g2d) {
        board.render(g2d);
    }
}
