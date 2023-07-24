package game;

import game.piece.EnemyPiece;
import game.piece.Piece;

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
    public static final int TILE_SIZE = 80;

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

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        private Item item;
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

    public static void main(String[] args) {
        generateChunk();
    }

    private enum Spawnable {
        ITEM (2), ENEMY (2), /*COIN (4),*/ CLOSED( 1);

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
     *
     * @return an array of newly generated tiles
     */
    private static Tile[][] generateChunk() {
        final Tile[][] chunk = new Tile[CHUNK_SIZE][CHUNK_SIZE];
        /*int baseGoodness = CHUNK_SIZE*CHUNK_SIZE/2;
        int goodness = (int) ((baseGoodness / 2) * Game.RANDOM.nextGaussian());*/
        int noToSpawn = Game.RANDOM.nextInt(4,8)
        Tile[] filledTiles = new Tile[noToSpawn];
        for (int i = 0; i < noToSpawn; i++) {
            Spawnable spawnable = Spawnable.getRandomWeighted();
            Tile t = null;
            switch (spawnable)
            {
                case ITEM -> {
                    t = new OpenTile();
                    t.setItem(Item.generateItem());
                }
                case ENEMY -> {
                    t = new OpenTile();
                    t.setPiece(EnemyPiece.generateEnemy());
                }
                /*case COIN -> {

                }*/
                case CLOSED -> {
                    t = new BlockedTile();
                }
            }
            filledTiles[i] = t;
        }
        for(Tile t : filledTiles)
        {
            int x = Game.RANDOM.nextInt(CHUNK_SIZE);
            int y = Game.RANDOM.nextInt(CHUNK_SIZE);
            while(chunk[x][y] == null)
            {
                chunk[x][y] = t;
                x = Game.RANDOM.nextInt(CHUNK_SIZE);
                y = Game.RANDOM.nextInt(CHUNK_SIZE);
            }
        }
        return chunk;
    }
}
