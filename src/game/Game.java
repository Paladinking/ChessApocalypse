package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {


    public static final Dimension SIZE = new Dimension(1920, 1080);

    private Board board;

    boolean passTurn = false;

    /**
     * Initializes the game.
     */
    void init() {

    }

    /**
     * Called once at the beginning of every turn.
     */
    void turn() {

    }

    /**
     * Main logic tick method.
     * @param delta time since last call to tick
     */
    void tick(long delta) {
        double secs = delta / 1000000000.0;
        if (passTurn) {
            turn();
            passTurn = false;
        }
    }

    /**
     * Renders one frame of the game
     * @param g2d the graphics context.
     */
    void render(Graphics2D g2d) {
        board.render(g2d);
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            passTurn = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
