package minesweeper.plus.views;

import com.badlogic.gdx.graphics.Texture;
import minesweeper.plus.core.SpotValues;

import java.util.HashMap;
import java.util.Map;

public class SimpleSpotTextures implements SpotTextures {
    private final Map<SpotValues, Texture> textures;
    SimpleSpotTextures() {
        textures = new HashMap<>();
        textures.put(SpotValues.FLAG, new Texture("02_flag.png"));
        textures.put(SpotValues.MINE, new Texture("03_bomb.png"));
        textures.put(SpotValues.HIDDEN, new Texture("00_hidden.png"));
        textures.put(SpotValues.N00, new Texture("01_num_00.png"));
        textures.put(SpotValues.N01, new Texture("01_num_01.png"));
        textures.put(SpotValues.N02, new Texture("01_num_02.png"));
        textures.put(SpotValues.N03, new Texture("01_num_03.png"));
        textures.put(SpotValues.N04, new Texture("01_num_04.png"));
        textures.put(SpotValues.N05, new Texture("01_num_05.png"));
        textures.put(SpotValues.N06, new Texture("01_num_06.png"));
        textures.put(SpotValues.N07, new Texture("01_num_07.png"));
        textures.put(SpotValues.N08, new Texture("01_num_08.png"));
        textures.put(SpotValues.N09, new Texture("01_num_09.png"));
        textures.put(SpotValues.N10, new Texture("01_num_10.png"));
    }
    public Texture getTexture(SpotValues sv) {
        return textures.get(sv);
    }
}
