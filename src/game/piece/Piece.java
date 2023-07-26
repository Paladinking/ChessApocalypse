package game.piece;

import game.Board;
import game.Game;
import game.Weighted;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

/**
 * Abstract chess piece
 */
public abstract class Piece {

    protected final static Image[] images;

    static {
        Image[] loaded_images = null;
        try {
            BufferedImage all = ImageIO.read(ClassLoader.getSystemResource("pieces.png"));
            if (all == null) throw new IOException("Could not load image 'pieces.png'");
            loaded_images = new Image[12];
            for (int x = 0; x < 6; x++) {
                for (int y = 0; y < 2; y++) {
                    loaded_images[x + 6 * y] = all.getSubimage(x * 100, y * 100, 100, 100);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load images: " + e);
            System.exit(-1);
        }
        images = loaded_images;
    }

    public enum PieceType implements Weighted {
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
    protected final MoveSet moveSet;
    protected final Image image;

    public Piece (int x, int y, int health, MoveSet moveSet, Image image) {
        this.position = new Point(x, y);
        this.health = health;
        this.moveSet = moveSet;
        this.image = image;
    }

    public Piece (int health, MoveSet moveSet, Image image) {
        this.position = null;
        this.moveSet = moveSet;
        this.image = image;
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(image, position.x * Board.TILE_SIZE, position.y * Board.TILE_SIZE,
                Board.TILE_SIZE, Board.TILE_SIZE, null);
    }

    /**
     * Internal class for movement
     */
    public static class MoveSet {
        private final HashSet<Point> moves;

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

    public MoveSet getMoveSet() {
        return moveSet;
    }

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
