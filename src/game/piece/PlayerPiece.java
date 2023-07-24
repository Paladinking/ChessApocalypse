package game.piece;

import java.awt.*;

public class PlayerPiece extends Piece{
    private boolean moved;
    public PlayerPiece(int x, int y) {
        super(x, y);
        moved = false;
    }

    public void move(Point target) {
        setPosition(target);
        moved = true;
    }

    public boolean isMoved() {
        return moved;
    }
}
