package game;

import java.awt.*;
import java.util.HashMap;


public class Board {
    public static final int TILE_SIZE = 80;

    HashMap<Point, Tile> map;

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
        abstract boolean isOpen();
    }

    public static class OpenTile extends Tile {
        String player;
        @Override
        boolean isOpen() {
            return player != null;
        }
    }

    public static class BlockedTile extends Tile {
        @Override
        boolean isOpen() {
            return false;
        }
    }
}
