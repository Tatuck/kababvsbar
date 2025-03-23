package com.tatuck.models;

import java.awt.image.BufferedImage;

import com.tatuck.controller.Map;
import com.tatuck.view.Tile;
import com.tatuck.view.PlayeableTexture;

public class PlayeableEntity extends Entity{
    public PlayeableTexture playeableTexture;

    public PlayeableEntity(int x, int y, Map map, PlayeableTexture playeableTexture){
        super(x, y, map);
        this.playeableTexture = playeableTexture;
    }

    @Override
    public BufferedImage getCurrentTexture(){
        return this.playeableTexture.getCurrentTexture();
    }

    public void move(int upDown, int rightLeft){
        super.move(upDown, rightLeft);
        PlayeableTexture.Direction newDirection = PlayeableTexture.Direction.DOWN;
        if (upDown > 0) newDirection = PlayeableTexture.Direction.UP;
        if (rightLeft > 0) newDirection = PlayeableTexture.Direction.RIGHT;
        if (rightLeft < 0) newDirection = PlayeableTexture.Direction.LEFT;
        this.playeableTexture.setDirection(newDirection);
    }
}
