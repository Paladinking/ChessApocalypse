package game.piece;

/**
 * Abstract chess piece
 */
public class Piece {
    private int x, y;
    private boolean player;
    public Piece (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Internal class for movement
     */
    private static abstract class MoveSet {
        private final int xMove;
        private final int yMove;
        public MoveSet(int xMove, int yMove){
            this.xMove = xMove;
            this.yMove = yMove;
        }

        public int getxMove() {
            return xMove;
        }

        public int getyMove() {
            return yMove;
        }
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
