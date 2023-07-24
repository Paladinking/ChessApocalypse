package game.piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Abstract chess piece
 */
public abstract class Piece {
    private Point position;
    private MoveSet moveSet;
    private int health;
    private boolean player;
    public Piece (int x, int y, int Health) {
        this.position = new Point(x, y);
    }

    /**
     * Internal class for movement
     */
    private static class MoveSet {
        private final ArrayList<Integer> xMoves;
        private final ArrayList<Integer> yMoves;
        public MoveSet(int xMove, int yMove, boolean shortMoves){
            xMoves = new ArrayList<>();
            yMoves = new ArrayList<>();
            xMoves.add(xMove);
            yMoves.add(yMove);
            if(shortMoves) {
                for (int i = xMove; i > 0; i--) {
                    xMoves.add(i);
                }
                for (int i = yMove; i > 0; i--) {
                    yMoves.add(i);
                }
            }
        }

        public ArrayList<Integer> getxMoves() {
            return xMoves;
        }

        public ArrayList<Integer>  getyMoves() {
            return yMoves;
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

    public HashSet<Point> ValidMoves() {
        HashSet<Point> temp = new HashSet<>();
        for (Integer move1: moveSet.getxMoves()) {
            for (Integer move2: moveSet.getyMoves()) {
                temp.add(new Point((int)position.getX() + move1, (int)position.getY() + move2));
            }
        }
        return temp;
    }

    public abstract void move(Point target);
}
