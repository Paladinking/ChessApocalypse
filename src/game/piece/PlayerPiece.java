package game.piece;

import java.awt.*;

public class PlayerPiece extends Piece {
    private boolean moved;
    public PlayerPiece(int x, int y, int health) {
        super(x, y, health);
        moved = false;

    }

    /**
     * moves a piece
     * @param target - coords of tile to move to
     */
    public void move(Point target) {
        setPosition(target);
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
