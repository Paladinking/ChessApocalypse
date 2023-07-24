package game;

import game.piece.Piece;
import game.piece.PlayerPiece;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class Board {
    public static final int TILE_SIZE = 80;

    HashMap<Point, Tile> map;

    List<PlayerPiece> players = new ArrayList<>();

    public void render(Graphics2D g2d, int cameraX, int cameraY) {
        g2d.setColor(Color.WHITE);
        int x = -Math.floorDiv(cameraX, TILE_SIZE);
        int y = -Math.floorDiv(cameraY, TILE_SIZE);
        for (int i = x - 1; i <= x + Game.SIZE.width / TILE_SIZE; i++) {
            for (int j = y -1; j <= y + Game.SIZE.height / TILE_SIZE ; j++) {
                g2d.setColor((i + j) % 2 == 0 ? Color.BLACK : Color.WHITE);
                g2d.fillRect(i * TILE_SIZE,  j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public abstract static class Tile {
        private Piece piece;
        abstract boolean isOpen();
        abstract boolean hasPiece();

        public Piece getPiece() {
            return piece;
        }

        public void setPiece(Piece piece) {
            this.piece = piece;
        }
    }

    public static class OpenTile extends Tile {
        @Override
        boolean isOpen() {
            return hasPiece();
        }

        //Temp
        @Override
        boolean hasPiece() {
            return getPiece() != null;
        }

    }

    public static class BlockedTile extends Tile {
        @Override
        boolean isOpen() {
            return false;
        }
        @Override
        boolean hasPiece() {
            return false;
        }
    }

    public boolean hasPiece(Point p){
        return map.get(p).hasPiece();
    }
}
