package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            JFrame frame = new JFrame();
            JPanel panel = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    Rectangle r = getBounds();
                    final double scaling = Math.min((double) r.width / Game.VIEWPORT.width,
                            (double) r.height / Game.VIEWPORT.height);
                    double heightDiff = (r.height - scaling * Game.VIEWPORT.height) / 2.0;
                    double widthDiff = (r.width - scaling * Game.VIEWPORT.width) / 2.0;
                    g2d.setClip((int) widthDiff, (int) heightDiff,
                            (int) (scaling * Game.VIEWPORT.width),
                            (int) (scaling * Game.VIEWPORT.height));
                    g2d.scale(scaling, scaling);
                    g2d.translate((int) (r.width / scaling - Game.VIEWPORT.width) / 2,
                            (int) (r.height / scaling - Game.VIEWPORT.height) / 2);
                    game.render(g2d);
                }
            };
            panel.setBackground(Color.BLACK);
            panel.setFocusable(true);
            panel.addKeyListener(game);
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point2D.Double relMousePos = getGameCoordinates(e.getX(), e.getY());
                    if (Game.VIEWPORT.contains(relMousePos)) {
                        game.mousePressed(relMousePos.x, relMousePos.y);
                    }
                }

                private Point2D.Double getGameCoordinates(double mouseX, double mouseY) {
                    Rectangle r = panel.getBounds();
                    final double scaling = Math.max(Game.VIEWPORT.width / (double) r.width,
                            Game.VIEWPORT.height / (double) r.height);
                    double heightDiff = (r.height - Game.VIEWPORT.height / scaling) / 2.0;
                    double widthDiff = (r.width - Game.VIEWPORT.width / scaling) / 2.0;
                    double x = mouseX - widthDiff, y = mouseY - heightDiff;
                    return new Point2D.Double(x * scaling, y * scaling);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    Point2D.Double relMousePos = getGameCoordinates(e.getX(), e.getY());
                    if (Game.VIEWPORT.contains(relMousePos)) {
                        game.mouseReleased(relMousePos.x, relMousePos.y);
                    }
                }
            });
            frame.setResizable(false);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.add(panel);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            final long[] timestamp = {System.nanoTime()};
            Timer timer = new Timer(15, (e) -> {
                long cur = System.nanoTime();
                game.tick(cur - timestamp[0]);
                timestamp[0] = cur;
                panel.repaint();
            });
            game.init();
            timer.start();
            frame.setVisible(true);
        });
    }
}
