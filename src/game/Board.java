package game;

import java.awt.*;
import java.util.HashMap;


public class Board {
    HashMap<Point, Tile> map;


    public void render(Graphics2D g2d) {

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
