package minesweeper.plus.views;

import com.badlogic.gdx.graphics.Texture;
import minesweeper.plus.core.SpotValues;

public interface SpotTextures {

    //takes the enum for a texture you want
    // and returns its internalPath (String)
    Texture getTexture(SpotValues sv);
}
