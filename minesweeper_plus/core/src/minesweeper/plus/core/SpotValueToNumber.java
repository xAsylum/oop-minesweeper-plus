package minesweeper.plus.core;

import java.util.HashMap;
import java.util.Map;

//quick translator used in communication
public class SpotValueToNumber {
    private static final Map<SpotValues, Integer> map;
    static {
        map = new HashMap<>();
        map.put(SpotValues.N00, 0);//
        map.put(SpotValues.N01, 1);
        map.put(SpotValues.N02, 2);
        map.put(SpotValues.N03, 3);
        map.put(SpotValues.N04, 4);
        map.put(SpotValues.N05, 5);//
        map.put(SpotValues.N06, 6);
        map.put(SpotValues.N07, 7);
        map.put(SpotValues.N08, 8);
        map.put(SpotValues.N09, 9);
        map.put(SpotValues.N10, 10);//
        map.put(SpotValues.N11, 11);
        map.put(SpotValues.N12, 12);
        map.put(SpotValues.N13, 13);
        map.put(SpotValues.N14, 14);
        map.put(SpotValues.N15, 15);//
        map.put(SpotValues.N16, 16);
        map.put(SpotValues.N17, 17);
        map.put(SpotValues.N18, 18);
        map.put(SpotValues.N19, 19);
        map.put(SpotValues.N20, 20);//
        map.put(SpotValues.N21, 21);
        map.put(SpotValues.N22, 22);
        map.put(SpotValues.N23, 23);
        map.put(SpotValues.N24, 24);
        map.put(SpotValues.N25, 25);//
        map.put(SpotValues.N26, 26);
    }
    public static Integer getValue(SpotValues v) {
        return map.get(v);
    }
}
