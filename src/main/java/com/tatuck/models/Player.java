package com.tatuck.models;

import java.time.LocalTime;
import java.util.Random;

import com.tatuck.controller.Map;
import com.tatuck.view.PlayeableTexture;
import com.tatuck.view.TextureManager;
import com.tatuck.view.PlayeableTexture.Direction;

public class Player extends PlayeableEntity{
    LocalTime lastShot;
    private int health;
    private String name;
    private int[] projectileTexturesID;
    public Player(String name, int[] projectileTexturesID, int x, int y, Map map, PlayeableTexture playeableTexture){
        super(x, y, map, playeableTexture);
        lastShot = LocalTime.now().minusSeconds(5);
        this.health = 100;
        this.name = name;
        this.projectileTexturesID = projectileTexturesID;
    }

    public void shoot(){
        Random r = new Random();
        LocalTime now = LocalTime.now();
        if (now.minusNanos(250000000).compareTo(lastShot) < 0) return;
        lastShot = now;
        Direction currentDirection = this.playeableTexture.getDirection();
        Projectile projectile = new Projectile(x, y, map, TextureManager.getInstance().getTexture(projectileTexturesID[r.nextInt(projectileTexturesID.length)]), currentDirection, this);
        this.map.addProjectile(projectile);
    }

    public int getHealth(){
        return this.health;
    }

    public void takeDamage(int damage){
        this.health = Math.max(0, this.health - damage);
    }

    @Override
    public String toString(){
        return this.name;
    }
}
