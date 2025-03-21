package com.tatuck.models;

import com.tatuck.controller.Map;
import com.tatuck.view.Tile;

public class Entity {
    public int x, y;
    public Map map;

    public Entity(int x, int y, Map map){
        this.x = x;
        this.y = y;
        this.map = map;
    }

    public boolean canBeIn(int newX, int newY) {
        Tile nextTile = map.getTileAtCoord(newX, newY);
        if (nextTile == null || nextTile.properties.getWalkable()){
            return false;
        }
        return true;
    }

    public void move(int upDown, int rightLeft){
        
    }
}
