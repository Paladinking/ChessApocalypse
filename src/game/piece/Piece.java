package game.piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Abstract chess piece
 */
public abstract class Piece {
    private Point position;
    private MoveSet moveSet = new MoveSet(1, 0, false);
    private int health;
    private boolean player;
    public Piece (int x, int y, int Health, boolean player) {
        this.position = new Point(x, y);
        this.player = player;
    }

    /**
     * Internal class for movement
     */
    public static class MoveSet {
        private final ArrayList<Point> moves;
        public MoveSet(int xMove, int yMove, boolean shortMoves){
            HashSet<Point> moves = new HashSet<>();
            if (shortMoves) {
                for (int i = 1; i <= xMove; i++) {
                    for (int j = 1; j <= yMove; j++) {
                        moves.add(new Point(xMove, yMove));
                        moves.add(new Point(-xMove, yMove));
                        moves.add(new Point(yMove, xMove));
                        moves.add(new Point(yMove, -xMove));

                        moves.add(new Point(xMove, -yMove));
                        moves.add(new Point(-xMove, -yMove));
                        moves.add(new Point(-yMove, xMove));
                        moves.add(new Point(-yMove, -xMove));
                    }
                }
            } else {
                moves.add(new Point(xMove, yMove));
                moves.add(new Point(-xMove, yMove));
                moves.add(new Point(yMove, xMove));
                moves.add(new Point(yMove, -xMove));

                moves.add(new Point(xMove, -yMove));
                moves.add(new Point(-xMove, -yMove));
                moves.add(new Point(-yMove, xMove));
                moves.add(new Point(-yMove, -xMove));
            }

            this.moves = new ArrayList<>(moves);
        }

        public ArrayList<Point> getMoves() {
            return moves;
        }
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public MoveSet getMoveSet() {
        return moveSet;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract void move(Point target);

    public boolean isPlayer() {
        return player;
    }
}
