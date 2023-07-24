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
    private int health, power;
    private final boolean player;
    public Piece (int x, int y, int health, boolean player) {
        this.position = new Point(x, y);
        this.player = player;
    }

    public Piece (int health, boolean player) {
        this.position = null;
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
