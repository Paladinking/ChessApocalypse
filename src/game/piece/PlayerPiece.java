package game.piece;

import game.App;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerPiece extends Piece {
    private boolean moved;

    private static Image getImage(PieceType type) {
        return switch (type) {
            case KNIGHT -> images[4];
            case PAWN -> images[5];
        };
    }

    public PlayerPiece(int x, int y, int health, PieceType type) {
        super(x, y, health, type.moveSet, getImage(type));
        moved = false;

    }

    /**
     * moves a piece
     * @param target - coords of tile to move to
     */
    public void move(Point target) {
        getPosition().setLocation(target);
        moved = true;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public boolean isMoved() {
        return moved;
    }
}
