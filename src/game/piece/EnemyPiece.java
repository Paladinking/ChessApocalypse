package game.piece;

import game.Board;

import java.awt.*;

public class EnemyPiece extends Piece {

    public EnemyPiece(int x, int y, int health) {
        super(x, y, health);
    }

    @Override
    public void move(Point target) {
        setPosition(target);
    }

    public void uppdate(Board board){
        Point
    }


}
