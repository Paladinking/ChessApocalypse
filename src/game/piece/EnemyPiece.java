package game.piece;

import game.Board;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EnemyPiece extends Piece {

    private static final int MAX_RANGE = 20;

    public EnemyPiece(int x, int y, int health, PieceType pieceType) {
        super(x, y, health, pieceType.moveSet);
    }

    public EnemyPiece(int health, PieceType pieceType) {
        super(health, pieceType.moveSet);
    }
    public static Piece generateEnemy() {
        return new EnemyPiece(2, PieceType.getRandomWeighted());
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

    public ArrayDeque<Point> moveTowards(Point goal, Board board, int steps) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(p ->
                p.cost + Math.abs(p.pos.x - goal.x) + Math.abs(p.pos.y - goal.y)));
        HashSet<Point> visited = new HashSet<>();
        int iterations = 0;
        queue.add(new Node(getPosition(), null, 0));
        final int MAX_ITERATIONS = 10_000;
        while (!queue.isEmpty() && iterations < MAX_ITERATIONS) {
            iterations++;
            Node n = queue.remove();
            if (n.pos.x == goal.x && n.pos.y == goal.y) {
                if (n.parent == null) return new ArrayDeque<>();
                Node next = n.parent;
                ArrayDeque<Point> res = new ArrayDeque<>();
                while (next.parent != null) {
                    res.push(n.pos);
                    n = next;
                    next = next.parent;
                }
                for (int i = 0; i < res.size() - steps; i++) {
                    res.removeFirst();
                }
                return res;
            }
            for (Point move : moveSet.getMoves()) {
                Point next = new Point(n.pos.x + move.x, n.pos.y + move.y);
                if (board.isOpen(next) && !visited.contains(next)) {
                    Node neighbor = new Node(next, n, n.cost + 1);
                    queue.add(neighbor);
                    visited.add(next);
                }
            }
        }
        return new ArrayDeque<>();
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
            Point move = moveTowards(closest.getPosition(), board, 1).poll();
            if (move != null) {

            }
        }
    }


}
