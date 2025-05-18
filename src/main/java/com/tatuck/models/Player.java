package com.tatuck.models;

import com.tatuck.controller.Map;
import com.tatuck.view.PlayeableTexture;

public class Player extends PlayeableEntity{
    private String name;
    public Player(String name, int[] projectileTexturesID, int x, int y, Map map, PlayeableTexture playeableTexture){
        super(x, y, map, playeableTexture, projectileTexturesID);
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
