package game;

import game.display.UI;
import game.piece.Piece;
import game.piece.PlayerPiece;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements KeyListener {

    public static final Dimension SIZE = new Dimension(1920, 1080);
    public static final int SEED = 8888;
    public static final Random RANDOM = new Random(SEED);

    private final Board board = new Board();

    private final UI ui = new UI();
    List<PlayerPiece> players = new ArrayList<>();

    private static final double PAN_SPEED = 300.0, FAST_PAN_SPEED = 600.0;

    private double cameraX = 0.0, cameraY = Board.TILE_SIZE / 4.0;
    private boolean up, down, left, right, fastPan;

    boolean passTurn = false;

    /**
     * Initializes the game.
     */
    void init() {
        Point pos = new Point(5, 5);
        PlayerPiece piece = new PlayerPiece(pos.x, pos.y, 10, Piece.PieceType.PAWN);
        players.add(piece);
        board.generateInitialMap();
        board.getTile(pos).setPiece(piece);
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
        double secs = delta / 1000_000_000.0;
        if (passTurn) {
            turn();
            passTurn = false;
        }
        final double pan_speed = (fastPan ? FAST_PAN_SPEED : PAN_SPEED);
        if (up) {
            cameraY += secs * pan_speed;
        }
        if (down) {
            cameraY -= secs * pan_speed;
        }
        if (left) {
            cameraX += secs * pan_speed;
        }
        if (right) {
            cameraX -= secs * pan_speed;
        }
    }

    /**
     * Renders one frame of the game
     * @param g2d the graphics context.
     */
    void render(Graphics2D g2d) {
        g2d.translate(cameraX, cameraY);
        board.render(g2d, (int) cameraX, (int) cameraY);
        for (PlayerPiece piece : players) {
            piece.render(g2d);
        }
        g2d.translate(-cameraX, -cameraY);
        ui.render(g2d);
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE -> passTurn = true;
            case KeyEvent.VK_UP, KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_SHIFT -> fastPan = true;
            case KeyEvent.VK_ESCAPE -> System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> right = false;
            case KeyEvent.VK_SHIFT -> fastPan = false;
        }
    }


    public void mousePressed(double x, double y) {
        int tileX = (int)Math.floor((x - cameraX) / Board.TILE_SIZE);
        int tileY = (int)Math.floor((y - cameraY) / Board.TILE_SIZE);
        ui.setSelected(board.getTile(new Point(tileX, tileY)));

    }

    public void mouseReleased(double x, double y) {

    }
}
