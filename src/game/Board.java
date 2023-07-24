package game;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;


public class Board {
    HashMap<Point, Tile> map;

    /**
     * The number of tiles in each direction in one chunk
     */
    private static final int CHUNK_SIZE = 8;

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

    /**
     * Generates a chunk of tiles with size Board.CHUNK_SIZE squared.
     * Items in the array are indexed left-to-right then top-down.
     *
     * @return an array of newly generated tiles
     */
    private static Tile[] generateChunk()
    {
        final Tile[] chunk = new Tile[CHUNK_SIZE*CHUNK_SIZE];
        Random rnd = new Random(Game.SEED);
        int baseGoodness = CHUNK_SIZE*CHUNK_SIZE/2;
        int goodness = baseGoodness + rnd.nextInt()

        return chunk;
    }
}
