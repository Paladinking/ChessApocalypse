package game.piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Abstract chess piece
 */
public abstract class Piece {
    private Point position;
    private int health, power;

    private MoveSet moveSet;

    public Piece (int x, int y, int Health) {
        this.position = new Point(x, y);
    }

    /**
     * Internal class for movement
     */
    public static class MoveSet {
        private final ArrayList<Point> moves;
        @SuppressWarnings("SuspiciousNameCombination")
        public MoveSet(int xMove, int yMove, boolean shortMoves) {
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

    public abstract boolean isPlayer();

    public void attack(Piece target){
        target.setHealth(target.getHealth() - power);
        Point temp = target.getPosition();
        if(target.getHealth() > 0) {
            int x = target.getPosition().x;
            int y = target.getPosition().y;
           switch (calcDirection(target.getPosition())) {
               case 1 -> {
                    target.move(new Point(x + 1, y + 1));
               }
               case 2 -> {
                   target.move(new Point(x, y + 1));
               }
               case 3 -> {
                   target.move(new Point(x - 1, y + 1));
               }
               case 4 -> {
                   target.move(new Point(x + 1, y));
               }
               case 5 -> {
                   target.move(new Point(x - 1, y - 1));
               }
               case 6 -> {
                   target.move(new Point(x, y - 1));
               }
               case 7 -> {
                   target.move(new Point(x + 1, y - 1));
               }
               case 8 -> {
                   target.move(new Point(x + 1, y));
               }
           }
        }
        move(temp);
    }

    /**
     * return a direction
     * 123
     * 8x4
     * 765
     */
    private int calcDirection(Point target) {
        int hDir, vDir;
        hDir = getPosition().x - target.x;
        vDir = getPosition().y - target.y;
        if(Math.abs(hDir) == Math.abs(vDir)) { //Diagonal movement
            if(hDir < 0 && vDir < 0){
                return 1;
            }
            else if(hDir > 0 && vDir < 0){
                return 3;
            }
            else if(hDir > 0 && vDir > 0){
                return 5;
            }
            else if(hDir < 0 && vDir > 0){
                return 7;
            }
        } else {
            if (hDir > vDir) {
                if (hDir < 0){
                    return 8;
                }else {
                    return 4;
                }
            } else {
                if (vDir < 0){
                    return 2;
                }else {
                    return 6;
                }
            }
        }
        return 0;
    }
}
