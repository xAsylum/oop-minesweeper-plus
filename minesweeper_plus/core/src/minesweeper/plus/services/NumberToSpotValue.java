package minesweeper.plus.services;

import java.util.HashMap;
import java.util.Map;


//quick translator used in communication
public class NumberToSpotValue {
    private static final Map<Integer, SpotValues> map;
    static {
        map = new HashMap<>();
        map.put(0, SpotValues.N00);//
        map.put(1, SpotValues.N01);
        map.put(2, SpotValues.N02);
        map.put(3, SpotValues.N03);
        map.put(4, SpotValues.N04);
        map.put(5, SpotValues.N05);//
        map.put(6, SpotValues.N06);
        map.put(7, SpotValues.N07);
        map.put(8, SpotValues.N08);
        map.put(9, SpotValues.N09);
        map.put(10, SpotValues.N10);//
        map.put(11, SpotValues.N11);
        map.put(12, SpotValues.N12);
        map.put(13, SpotValues.N13);
        map.put(14, SpotValues.N14);
        map.put(15, SpotValues.N15);//
        map.put(16, SpotValues.N16);
        map.put(17, SpotValues.N17);
        map.put(18, SpotValues.N18);
        map.put(19, SpotValues.N19);
        map.put(20, SpotValues.N20);//
        map.put(21, SpotValues.N21);
        map.put(22, SpotValues.N22);
        map.put(23, SpotValues.N23);
        map.put(24, SpotValues.N24);
        map.put(25, SpotValues.N25);//
        map.put(26, SpotValues.N26);
    }
    public static SpotValues getSpotValue(int n) {
        return map.get(n);
    }
}
