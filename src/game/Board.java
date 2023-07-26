package game;

import game.piece.EnemyPiece;
import game.piece.Piece;

import java.awt.*;
import java.util.HashMap;


public class Board {
    private final HashMap<Point, Tile> map;

    public Board() {
        map = new HashMap<>();
    }

    public void generateInitialMap() {
        //4
        int chunksHorizontal = (int) ((Game.VIEWPORT.width / (double) TILE_SIZE) / CHUNK_SIZE) + 1;
        //2
        int chunksVertical = (int) ((Game.VIEWPORT.height / (double) TILE_SIZE) / CHUNK_SIZE) + 1;

        int x = -(chunksHorizontal / 2) * CHUNK_SIZE;
        int y = -(chunksVertical / 2) * CHUNK_SIZE;

        for (int i = 0; i < chunksHorizontal; i++) {
            for (int j = 0; j < chunksVertical; j++) {
                loadChunk(x + CHUNK_SIZE * i, y + CHUNK_SIZE * j);
            }
        }
    }

    /**
     * Generates a new chunk and places all
     * tiles within it on the board as well as placing
     * items and pieces belonging to them on the board
     * @param startX x coordinate of the left edge of the chunk
     * @param startY y coordinate of the top of the chunk
     */
    private void loadChunk(int startX, int startY) {
        Tile[][] chunk = Tile.generateChunk();

        for (int x = 0; x < chunk.length; x++) {
            for (int y = 0; y < chunk[x].length; y++) {
                Point position = new Point(startX + x, startY + y);
                map.put(position, chunk[x][y]);
                if(chunk[x][y].hasPiece()) {
                    chunk[x][y].getPiece().setPosition(position);
                }
                else if (chunk[x][y].hasItem()) {
                    chunk[x][y].getItem().setPosition(position);
                }
            }
        }
    }

    /**
     * The number of tiles in each direction in one chunk
     */
    public static final int CHUNK_SIZE = 8;
    public static final int TILE_SIZE = 80;

    public void render(Graphics2D g2d, int cameraX, int cameraY, Tile selected) {
        g2d.setColor(Color.WHITE);
        int x = -Math.floorDiv(cameraX, TILE_SIZE);
        int y = -Math.floorDiv(cameraY, TILE_SIZE);

        for (int i = x - 1; i <= x + Game.VIEWPORT.width / TILE_SIZE; i++) {
            for (int j = y - 2; j <= y + Game.VIEWPORT.height / TILE_SIZE; j++) {
                g2d.setColor((i + j) % 2 == 0 ? Color.BLACK : new Color(255, 210, 153, 255));
                g2d.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        if(selected != null) {
            if (selected.hasPiece()) {
                for (Point point : selected.getPiece().getMoveSet().getMoves()) {
                    g2d.setColor(Color.YELLOW);
                    g2d.fillRect(selected.getPiece().getPosition().x + point.x * TILE_SIZE,
                            selected.getPiece().getPosition().y + point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    public boolean hasPiece(Point p) {
        return map.get(p).hasPiece();
    }

    public Tile getTile(Point p) {
        return map.get(p);
    }

    public boolean isOpen(Point p) {
        Tile t = map.get(p);
        if (t != null) {
            return t.isOpen();
        }
        return true;
    }


}
