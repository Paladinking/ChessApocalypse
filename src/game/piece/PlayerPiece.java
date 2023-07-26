package game.piece;

import java.awt.*;

public class PlayerPiece extends Piece {
    private boolean moved;
    public PlayerPiece(int x, int y, int health, PieceType type) {
        super(x, y, health, type.moveSet);
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
