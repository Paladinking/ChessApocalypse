package game.piece;

import game.Board;

import java.awt.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class EnemyPiece extends Piece {

    private static final int MAX_RANGE = 20;

    public EnemyPiece(int x, int y, int health) {
        super(x, y, health);
    }

    public EnemyPiece(int health) {
        super(health);
    }
    public static Piece generateEnemy() {
        return new EnemyPiece(2 );
    }

    @Override
    public void move(Point target) {
        getPosition().setLocation(target);
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    private record Node(Point pos, Node parent, int cost) {
    }

    public Point moveTowards(Point goal, Board board) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(p ->
                p.cost + Math.abs(p.pos.x - goal.x) + Math.abs(p.pos.y - goal.y)));
        HashSet<Point> visited = new HashSet<>();
        int iterations = 0;
        queue.add(new Node(getPosition(), null, 0));
        final int MAX_ITERATIONS = 10000;
        while (!queue.isEmpty() && iterations < MAX_ITERATIONS) {
            iterations++;
            Node n = queue.remove();
            if (n.pos.x == goal.x && n.pos.y == goal.y) {
                if (n.parent == null) return null;
                Node next = n.parent;
                while (next.parent != null) {
                    n = next;
                    next = next.parent;
                }
                return n.pos;
            }
            for (Point move : getMoveSet().getMoves()) {
                Point next = new Point(n.pos.x + move.x, n.pos.y + move.y);
                if (board.isOpen(next) && !visited.contains(next)) {
                    Node neighbor = new Node(next, n, n.cost + Math.abs(move.x) + Math.abs(move.y));
                    queue.add(neighbor);
                    visited.add(next);
                }
            }
        }
        return null;
    }

    public void update(Board board, List<PlayerPiece> players) {
        int closestDistance = MAX_RANGE;
        PlayerPiece closest = null;
        for (PlayerPiece p : players) {
            Point pos = p.getPosition();
            int dist = Math.abs(pos.x - getPosition().x) + Math.abs(pos.y - getPosition().y);
            if (dist < closestDistance) {
                closest = p;
                closestDistance = dist;
            }
        }
        if (closest != null) {
            Point move = moveTowards(closest.getPosition(), board);
            if (move != null) {

            }
        }
    }


}
