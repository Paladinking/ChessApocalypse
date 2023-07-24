package game.piece;

import game.Board;

import java.awt.*;

public class EnemyPiece extends Piece {

    public EnemyPiece(int x, int y, int health) {
        super(x, y, health, false);
    }

    public EnemyPiece(int health) {
        super(health,false);
    }
    public static Piece generateEnemy() {
        return new EnemyPiece(2 );
    }

    @Override
    public void move(Point target) {
        setPosition(target);
    }

    public void update(Board board){
        Point target = new Point(0, 0);
        if(board.hasPiece(target)) {

        }
    }


}
