package game;

import game.piece.EnemyPiece;
import game.piece.Piece;

import java.awt.*;

public class Tile {

    private Piece piece;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    private Item item;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isOpen() {
        return hasPiece() || hasItem();
    }

    public boolean hasPiece() {
        return piece != null;
    }

    boolean hasItem() {
        return item != null;
    }

    private enum Spawnable implements Weighted {
        ITEM, ENEMY, CLOSED;

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
    public static Tile[][] generateChunk() {
        final Tile[][] chunk = new Tile[Board.CHUNK_SIZE][Board.CHUNK_SIZE];
        int noToSpawn = Game.RANDOM.nextInt(Board.CHUNK_SIZE, Board.CHUNK_SIZE * Board.CHUNK_SIZE / 2);
        Tile[] filledTiles = new Tile[noToSpawn];
        for (int i = 0; i < noToSpawn; i++) {
            Spawnable spawnable = Spawnable.getRandomWeighted();
            Tile t = null;
            switch (spawnable) {
                case ITEM -> {
                    t = new Tile();
                    t.setItem(Item.generateItem());
                }
                case ENEMY -> {
                    t = new Tile();
                    t.setPiece(EnemyPiece.generateEnemy());
                }
                case CLOSED -> {
                    t = new Tile();
                    //TODO: add blocked item type
                }
            }
            filledTiles[i] = t;
        }
        for (Tile t : filledTiles) {
            int x = Game.RANDOM.nextInt(Board.CHUNK_SIZE);
            int y = Game.RANDOM.nextInt(Board.CHUNK_SIZE);
            while (chunk[x][y] != null) {
                x = Game.RANDOM.nextInt(Board.CHUNK_SIZE);
                y = Game.RANDOM.nextInt(Board.CHUNK_SIZE);
            }
            chunk[x][y] = t;
        }

        for (int x = 0; x < chunk.length; x++) {
            for (int y = 0; y < chunk[x].length; y++) {
                if (chunk[x][y] == null) {
                    chunk[x][y] = new Tile();
                }
            }
        }
        return chunk;
    }
}
