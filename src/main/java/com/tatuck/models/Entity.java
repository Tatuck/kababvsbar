package com.tatuck.models;

import java.awt.image.BufferedImage;

import com.tatuck.controller.Map;
import com.tatuck.view.Tile;
import com.tatuck.view.PlayeableTexture;

public class Entity {
    public int x, y;
    public Map map;
    public float defaultSpeed = 1f;
    public int sizeX, sizeY = 32;
    public PlayeableTexture playeableTexture;

    public Entity(int x, int y, Map map, PlayeableTexture playeableTexture){
        this.x = x;
        this.y = y;
        this.map = map;
        this.playeableTexture = playeableTexture;
    }

    public boolean canBeIn(int newX, int newY) {
        Tile nextTile = map.getTileAtScreenCoord(newX, newY);
        if (nextTile == null || !nextTile.properties.getWalkable()){
            return false;
        }
        return true;
    }

    public void move(int upDown, int rightLeft){
        Tile currentTile = map.getTileAtScreenCoord(x, y);
        int newX = (int) (x + rightLeft * defaultSpeed * currentTile.properties.getSpeedMultiplier());
        int newY = (int) (y - upDown * defaultSpeed * currentTile.properties.getSpeedMultiplier());
        BufferedImage currentTexture = this.playeableTexture.getCurrentTexture();
        if (this.canBeIn(newX + Math.max(0, rightLeft * currentTexture.getWidth()), newY - Math.min(0, upDown * currentTexture.getHeight()))){
            this.x = newX;
            this.y = newY;
            PlayeableTexture.Direction newDirection = PlayeableTexture.Direction.DOWN;
            if (upDown > 0) newDirection = PlayeableTexture.Direction.UP;
            if (rightLeft > 0) newDirection = PlayeableTexture.Direction.RIGHT;
            if (rightLeft < 0) newDirection = PlayeableTexture.Direction.LEFT;
            this.playeableTexture.setDirection(newDirection);
        }
    }
}
