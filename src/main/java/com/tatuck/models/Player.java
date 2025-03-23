package com.tatuck.models;

import java.time.LocalTime;

import com.tatuck.controller.Map;
import com.tatuck.view.PlayeableTexture;
import com.tatuck.view.TextureManager;
import com.tatuck.view.PlayeableTexture.Direction;

public class Player extends PlayeableEntity{
    LocalTime lastShot;
    public Player(int x, int y, Map map, PlayeableTexture playeableTexture){
        super(x, y, map, playeableTexture);
        lastShot = LocalTime.now().minusSeconds(5);
    }

    public void shoot(){
        LocalTime now = LocalTime.now();
        if (now.minusNanos(250000000).compareTo(lastShot) < 0) return;
        lastShot = now;
        Direction currentDirection = this.playeableTexture.getDirection();
        Projectile projectile = new Projectile(x, y, map, TextureManager.getInstance().getTexture(300), currentDirection);
        this.map.addProjectile(projectile);
    }
}
