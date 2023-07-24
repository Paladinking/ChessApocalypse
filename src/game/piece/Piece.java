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
    private MoveSet moveSet;
    private int health, power;
    private final boolean player;
    public Piece (int x, int y, int Health, boolean player) {
        this.position = new Point(x, y);
        this.player = player;
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

    public boolean isPlayer() {
        return player;
    }

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
