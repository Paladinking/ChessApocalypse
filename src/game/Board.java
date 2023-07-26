package game;

import game.piece.EnemyPiece;
import game.piece.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Board {
    private final HashMap<Point, Tile> map;

    public Board() {
        map = new HashMap<>();
        generateInitialMap();
    }
    private void generateInitialMap() {
        //4
        int chunksHorizontal = (int) ((Game.SIZE.width / (double) TILE_SIZE) / CHUNK_SIZE) + 1;
        //2
        int chunksVertical = (int) ((Game.SIZE.height / (double)TILE_SIZE) / CHUNK_SIZE) + 1;

        int x = - (chunksHorizontal / 2) * CHUNK_SIZE;
        int y = - (chunksVertical / 2) * CHUNK_SIZE;

        for (int i = 0; i < chunksHorizontal; i++) {
            for (int j = 0; j < chunksVertical; j++) {
                loadChunk(x + CHUNK_SIZE*i,y + CHUNK_SIZE * j);
            }
        }
    }

    private void loadChunk(int startX, int startY) {
        Tile[][] chunk = generateChunk();

        for (int x = 0; x < chunk.length; x++) {
            for (int y = 0; y < chunk[x].length; y++) {
                map.put(new Point(startX + x, startY + y), chunk[x][y]);
            }
        }
    }
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
            for (int j = y -2; j <= y + Game.SIZE.height / TILE_SIZE ; j++) {
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
    public boolean isOpen(Point p) {
        Tile t = map.get(p);
        if (t != null) {
            return t.isOpen();
        }
        return true;
    }

    private enum Spawnable implements Weighted {
        ITEM, ENEMY, /*COIN,*/ CLOSED;

        public int getWeight() {
            return switch (this) {
                case ITEM, ENEMY -> 2;
                case CLOSED -> 1;
            };
        }

        public static Spawnable getRandomWeighted() {
            return (Spawnable) Weighted.getRandomWeighted(values());
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
        int noToSpawn = Game.RANDOM.nextInt(CHUNK_SIZE,CHUNK_SIZE*CHUNK_SIZE / 2);
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
            while(chunk[x][y] != null)
            {
                x = Game.RANDOM.nextInt(CHUNK_SIZE);
                y = Game.RANDOM.nextInt(CHUNK_SIZE);
            }
            chunk[x][y] = t;
        }

        for (int x = 0; x < chunk.length; x++) {
            for (int y = 0; y < chunk[x].length; y++) {
                if(chunk[x][y] == null)
                {
                    chunk[x][y] = new OpenTile();
                }
            }
        }
        return chunk;
    }
}
