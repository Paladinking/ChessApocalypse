package game;

import java.util.ArrayList;
import java.util.Arrays;

public interface Weighted {
    int getWeight();

    static Weighted getRandomWeighted(Weighted[] values) {
        int weightSum = Arrays.stream(values).map(Weighted::getWeight).reduce(0, Integer::sum);
        ArrayList<Weighted> weightedList = new ArrayList<>(weightSum);
        for(Weighted s : values)
        {
            for(int i = 0; i < s.getWeight(); i++)
            {
                weightedList.add(s);
            }
        }
        return weightedList.get(Game.RANDOM.nextInt(weightSum));
    }
}
