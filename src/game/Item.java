package game;

import java.awt.*;

public abstract class Item {

    private Point position;
    public static Item generateItem()
    {
        return new Item(){};
    };

    public void setPosition(Point position) {
        this.position = position;
    }
}
