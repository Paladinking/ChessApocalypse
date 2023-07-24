package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


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

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            generateChunk();
        }
    }

    private enum Spawnable {
        ITEM (1), ENEMY (1), COIN (2);

        private Spawnable(int weight) {
            this.weight = weight;
        }
        public final int weight;

        public static Spawnable getRandomWeighted() {
            int weightSum = Arrays.stream(Spawnable.values()).map(s->s.weight).reduce(0, Integer::sum);
            ArrayList<Spawnable> weightedList = new ArrayList<>(weightSum);
            for(Spawnable s : Spawnable.values())
            {
                for(int i = 0; i < s.weight; i++)
                {
                    weightedList.add(s);
                }
            }
            return weightedList.get(Game.RANDOM.nextInt(weightSum));
        }
    }

    /**
     * Generates a chunk of tiles with size Board.CHUNK_SIZE squared.
     * Items in the array are indexed left-to-right then top-down.
     *
     * @return an array of newly generated tiles
     */
    private static Tile[] generateChunk() {
        final Tile[] chunk = new Tile[CHUNK_SIZE*CHUNK_SIZE];
        int baseGoodness = CHUNK_SIZE*CHUNK_SIZE/2;
        int goodness = (int) ((baseGoodness / 2) * Game.RANDOM.nextGaussian());
        int sum = 0;


        Spawnable spawnable = Spawnable.getRandomWeighted();
        System.out.println(spawnable);

        return chunk;
    }
}
