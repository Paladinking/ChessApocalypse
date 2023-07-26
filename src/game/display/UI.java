package game.display;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    private final ArrayList<Button> buttons;
    public UI() {
        buttons = new ArrayList<>();
        Button peiceButton = new Button(100, 100, 100, 50);
        buttons.add(peiceButton);
    }

    public void render(Graphics2D gd2){
        for (Button b:buttons) {
            b.draw(gd2);
        }
    }
}
