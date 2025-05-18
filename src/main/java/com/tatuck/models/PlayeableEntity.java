package com.tatuck.models;

import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.Random;

import com.tatuck.controller.Map;
import com.tatuck.view.PlayeableTexture;
import com.tatuck.view.PlayeableTexture.Direction;
import com.tatuck.view.TextureManager;

public class PlayeableEntity extends Entity{
    public PlayeableTexture playeableTexture;
    protected int health;
    LocalTime lastShot;
    protected double secsPerShoot = 0.25;
    private int[] projectileTexturesID;

    public PlayeableEntity(int x, int y, Map map, PlayeableTexture playeableTexture, int[] projectileTexturesID){
        super(x, y, map);
        this.playeableTexture = playeableTexture;
        lastShot = LocalTime.now().minusSeconds(5);
        this.health = 100;
        this.projectileTexturesID = projectileTexturesID;
    }

    @Override
    public BufferedImage getCurrentTexture(){
        return this.playeableTexture.getCurrentTexture();
    }

    public void move(int upDown, int rightLeft){
        // updown: > 0 up
        // rightLeft: > 0 right
        super.move(upDown, rightLeft);
        PlayeableTexture.Direction newDirection = PlayeableTexture.Direction.DOWN;
        if (upDown > 0) newDirection = PlayeableTexture.Direction.UP;
        if (rightLeft > 0) newDirection = PlayeableTexture.Direction.RIGHT;
        if (rightLeft < 0) newDirection = PlayeableTexture.Direction.LEFT;
        this.playeableTexture.setDirection(newDirection);
    }

    public void shoot(){
        Random r = new Random();
        LocalTime now = LocalTime.now();
        if (now.minusNanos((long)(secsPerShoot*1000000000)).compareTo(lastShot) < 0) return;
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
}
