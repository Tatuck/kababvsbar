package com.tatuck.models;

import com.tatuck.controller.Map;
import com.tatuck.view.Tile;

public class Entity {
    public int x, y;
    public Map map;
    public float defaultSpeed = 1f;
    public int sizeX, sizeY = 32;

    public Entity(int x, int y, Map map){
        this.x = x;
        this.y = y;
        this.map = map;
    }

    public Entity(int x, int y, Map map, float defaultSpeed){
        this(x, y, map);
        this.defaultSpeed = defaultSpeed;
    }

    public boolean canBeIn(int newX, int newY) {
        Tile nextTile = map.getTileAtCoord(newX, newY);
        if (nextTile == null || nextTile.properties.getWalkable()){
            return false;
        }
        return true;
    }

    public void move(int upDown, int rightLeft){
        int newX = (int) (x + rightLeft * defaultSpeed);
        int newY = (int) (y + upDown * defaultSpeed);
        if (!this.canBeIn(newX, newY)){
            this.x = newX;
            this.y = newY;
        }
    }
}
