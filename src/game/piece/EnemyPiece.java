package game.piece;

import java.awt.*;

public class EnemyPiece extends Piece {

    public EnemyPiece(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(Point target) {
        setPosition(target);
    }


}
