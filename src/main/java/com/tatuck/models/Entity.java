package com.tatuck.models;

import java.awt.image.BufferedImage;

import com.tatuck.controller.Map;
import com.tatuck.view.Tile;
public class Entity {
    public int x, y;
    public Map map;
    public float defaultSpeed = 1f;
    private BufferedImage texture;

    public Entity(int x, int y, Map map){
        this.x = x;
        this.y = y;
        this.map = map;
    }

    public Entity(int x, int y, Map map, BufferedImage texture){
        this(x, y, map);
        this.texture = texture;
    }

    public boolean canBeIn(int newX, int newY) {
        Tile nextTile = map.getTileAtScreenCoord(newX, newY);
        if (nextTile == null || !nextTile.properties.getWalkable()){
            return false;
        }
        return true;
    }

    public BufferedImage getCurrentTexture(){
        return this.texture;
    }

    public void move(int upDown, int rightLeft){
        Tile currentTile = map.getTileAtScreenCoord(x, y);
        int newX = (int) (x + rightLeft * defaultSpeed * currentTile.properties.getSpeedMultiplier());
        int newY = (int) (y - upDown * defaultSpeed * currentTile.properties.getSpeedMultiplier());
        BufferedImage currentTexture = getCurrentTexture(); // TODO: fix bug walking through unkalbable tiles if the entity isn't fully inside the tile
        if (this.canBeIn(newX + Math.max(0, rightLeft * currentTexture.getWidth()), newY - Math.min(0, upDown * currentTexture.getHeight()))){
            this.x = newX;
            this.y = newY;
        }
    }

    public boolean intersectsWith(Entity otherEntity){
        if (otherEntity.getCurrentTexture() == null) return false;
        int otherEntityLeft = otherEntity.x;
        int otherEntityRight = otherEntity.x + otherEntity.getCurrentTexture().getWidth();
        int otherEntityTop = otherEntity.y;
        int otherEntityBottom = otherEntity.y + otherEntity.getCurrentTexture().getHeight();
        return this.x < otherEntityRight && this.x + this.getCurrentTexture().getWidth() > otherEntityLeft && this.y < otherEntityBottom && this.y + this.getCurrentTexture().getHeight() > otherEntityTop;
    }
}
