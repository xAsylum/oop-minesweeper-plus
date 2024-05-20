package minesweeper.plus.views;

import minesweeper.plus.services.SpotValues;

import java.util.HashMap;
import java.util.Map;

public class SimpleSpotTextures implements SpotTextures {
    private final Map<SpotValues, String> textures;
    SimpleSpotTextures() {
        textures = new HashMap<>();
        textures.put(SpotValues.FLAG, "02_flag.png");
        textures.put(SpotValues.MINE, "03_bomb.png");
        textures.put(SpotValues.HIDDEN, "00_hidden.png");
        textures.put(SpotValues.N00, "01_num_00.png");
        textures.put(SpotValues.N01, "01_num_01.png");
        textures.put(SpotValues.N02, "01_num_02.png");
        textures.put(SpotValues.N03, "01_num_03.png");
        textures.put(SpotValues.N04, "01_num_04.png");
        textures.put(SpotValues.N05, "01_num_05.png");
        textures.put(SpotValues.N06, "01_num_06.png");
        textures.put(SpotValues.N07, "01_num_07.png");
        textures.put(SpotValues.N08, "01_num_08.png");
        textures.put(SpotValues.N09, "01_num_09.png");
        textures.put(SpotValues.N10, "01_num_10.png");
    }
    public String getTexture(SpotValues sv) {
        return textures.get(sv);
    }
}
