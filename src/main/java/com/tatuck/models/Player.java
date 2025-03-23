package com.tatuck.models;

import com.tatuck.controller.Map;
import com.tatuck.view.PlayeableTexture;
import com.tatuck.view.TextureManager;
import com.tatuck.view.PlayeableTexture.Direction;

public class Player extends PlayeableEntity{
    public Player(int x, int y, Map map, PlayeableTexture playeableTexture){
        super(x, y, map, playeableTexture);
    }

    public void shoot(){
        Direction currentDirection = this.playeableTexture.getDirection();
        
        Projectile projectile = new Projectile(x, y, map, TextureManager.getInstance().getTexture(300), currentDirection);
        this.map.addProjectile(projectile);
    }
}
