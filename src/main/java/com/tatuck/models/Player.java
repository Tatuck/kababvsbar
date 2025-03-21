package com.tatuck.models;

import com.tatuck.controller.Map;
import com.tatuck.view.PlayeableTexture;

public class Player extends Entity{
    public Player(int x, int y, Map map, PlayeableTexture playeableTexture){
        super(x, y, map, playeableTexture);
    }
}
