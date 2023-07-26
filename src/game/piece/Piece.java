package game.piece;

import game.Board;
import game.Weighted;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Abstract chess piece
 */
public abstract class Piece {

    protected enum PieceType implements Weighted {
        KNIGHT(new MoveSet().withSymmetricMoves(2, 1)),
        PAWN(new MoveSet().withSymmetricMoves(1, 0));

        public final MoveSet moveSet;
        PieceType(MoveSet moveSet) {
            this.moveSet = moveSet;
        }

        @Override
        public int getWeight() {
            return switch (this) {
                case KNIGHT -> 1;
                case PAWN -> 5;
            };
        }

        public static PieceType getRandomWeighted() {
            return (PieceType) Weighted.getRandomWeighted(values());
        }
    }

    protected Point position;
    protected int health, power = 10;
    protected MoveSet moveSet;

    public Piece (int x, int y, int health, MoveSet moveSet) {
        this.position = new Point(x, y);
        this.health = health;
        this.moveSet = moveSet;
    }

    public Piece (int health, MoveSet moveSet) {
        this.position = null;
    }

    /**
     * Internal class for movement
     */
    public static class MoveSet {
        private final HashSet<Point> moves;
        @SuppressWarnings("SuspiciousNameCombination")

        public MoveSet() {
            this.moves = new HashSet<>();
        }

        @SuppressWarnings("SuspiciousNameCombination")
        public MoveSet withSymmetricMoves(int xMove, int yMove) {
            moves.add(new Point(xMove, yMove));
            moves.add(new Point(-xMove, yMove));
            moves.add(new Point(yMove, xMove));
            moves.add(new Point(yMove, -xMove));

            moves.add(new Point(xMove, -yMove));
            moves.add(new Point(-xMove, -yMove));
            moves.add(new Point(-yMove, xMove));
            moves.add(new Point(-yMove, -xMove));
            return this;
        }

        public MoveSet withSymmetricMovesTo(int xMove, int yMove) {
            assert xMove >= 0 && yMove >= 0 && xMove + yMove > 0;
            for (int i = 1; i <= xMove; i++) {
                for (int j = 1; j <= yMove; j++) {
                    this.withSymmetricMoves(i, j);
                }
            }
            return this;
        }

        public MoveSet withMove(int xMove, int yMove) {
            moves.add(new Point(xMove, yMove));
            return this;
        }

        public HashSet<Point> getMoves() {
            return moves;
        }
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
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
            if (board.hasPiece(calcDirection(target.getPosition()))) {
                label: for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (!board.hasPiece(new Point(x - 1 + i, y - 1 + j))) {
                            target.move(new Point(x - 1 + i, y - 1 + j));
                            break label;
                        }
                    }
                }
            } else {
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
        int x = target.x;
        int y = target.y;
        if(Math.abs(hDir) == Math.abs(vDir)) { //Diagonal movement
            if (hDir < 0 && vDir < 0){
                return new Point(x + 1, y + 1);
            }
            else if (hDir > 0 && vDir < 0){
                return new Point(x - 1, y + 1);
            }
            else if (hDir > 0 && vDir > 0){
                return new Point(x - 1, y - 1);
            }
            else if (hDir < 0 && vDir > 0){
                return new Point(x + 1, y - 1);
            }
        } else {
            if (hDir > vDir) {
                return new Point(x + 1, y);
            } else {
                if (vDir < 0){
                    return new Point(x, y + 1);
                } else {
                    return new Point(x, y - 1);
                }
            }
        }
        return null;
    }
}
