package game;

import javax.swing.*;
import java.awt.*;

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
                    Rectangle r = g2d.getClipBounds();
                    final double scaling = Math.min((double)r.width / Game.SIZE.width,
                            (double) r.height / Game.SIZE.height);
                    g2d.scale(scaling, scaling);
                    g2d.translate((int)(r.width / scaling - Game.SIZE.width) / 2,
                            (int)(r.height / scaling - Game.SIZE.height) / 2);
                    game.render(g2d);
                }
            };
            panel.setBackground(Color.BLACK);
            panel.setFocusable(true);
            panel.addKeyListener(game);
            frame.setResizable(false);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.add(panel);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            final long[] timestamp = {System.nanoTime()};
            Timer timer = new Timer(15, (e) -> {
                long cur = System.nanoTime();
                game.tick(cur - timestamp[0]);
                timestamp[0] = cur;
                panel.repaint();
            });
            timer.start();
        });
    }
}
