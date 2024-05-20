package minesweeper.plus.views;

import minesweeper.plus.services.SpotValues;

public interface SpotTextures {

    //takes the enum for a texture you want
    // and returns its internalPath (String)
    String getTexture(SpotValues sv);
}
