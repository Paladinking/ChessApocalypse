package game;

import java.awt.*;

public class Item {

    ItemType itemType;
    public Item(ItemType itemType) {
        this.itemType = itemType;
    }
    private Point position;
    public static Item generateItem()
    {
        return new Item(ItemType.getRandomWeighted());
    };

    private enum ItemType implements Weighted{
        HEAL, DAMAGE, COIN;

        @Override
        public int getWeight() {
            return switch (this) {
                case HEAL -> 1;
                case DAMAGE -> 2;
                case COIN -> 3;
            };
        }
        private static ItemType getRandomWeighted() {
            return (ItemType) Weighted.getRandomWeighted(values());
        }
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
