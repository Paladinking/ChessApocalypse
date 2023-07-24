package game.piece;

import game.Board;

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

    public void attack(Piece target, Board board){
        target.setHealth(target.getHealth() - power);
        Point temp = target.getPosition();
        if(target.getHealth() > 0) {
            int x = target.getPosition().x;
            int y = target.getPosition().y;
            if(board.hasPiece(calcDirection(target.getPosition()))) {
                Point p = target.getPosition();
                label: for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (!board.hasPiece(new Point(target.getPosition().x-1 + i, target.getPosition().y-1 + j))) {
                            target.move(new Point(target.getPosition().x-1 + i, target.getPosition().y-1 + j));
                            break label;
                        }
                    }
                }
            }else {
                target.move(calcDirection(target.getPosition()));
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
    private Point calcDirection(Point target) {
        int hDir, vDir;
        hDir = getPosition().x - target.x;
        vDir = getPosition().y - target.y;
        int dir = 0;
        int x = target.x;
        int y = target.y;
        if(Math.abs(hDir) == Math.abs(vDir)) { //Diagonal movement
            if(hDir < 0 && vDir < 0){
                dir = 1;
                return new Point(x + 1, y + 1);
            }
            else if(hDir > 0 && vDir < 0){
                dir = 3;
                return new Point(x - 1, y + 1);
            }
            else if(hDir > 0 && vDir > 0){
                dir = 5;
                return new Point(x - 1, y - 1);
            }
            else if(hDir < 0 && vDir > 0){
                dir = 7;
                return new Point(x + 1, y - 1);
            }
        } else {
            if (hDir > vDir) {
                if (hDir < 0){
                    dir = 8;
                    return new Point(x + 1, y);
                }else {
                    dir = 4;
                    return new Point(x + 1, y);
                }
            } else {
                if (vDir < 0){
                    dir = 2;
                    return new Point(x, y + 1);
                }else {
                    dir = 6;
                    return new Point(x, y - 1);
                }
            }
        }
        return null;
    }
}
