package com.tatuck.models;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.tatuck.controller.Map;
import com.tatuck.view.PlayeableTexture.Direction;

public class Projectile extends Entity {
    private int damage = 10;
    private boolean death = false;
    private Direction direction;
    private Player shooter;

    public Projectile(int x, int y, Map map, BufferedImage texture, Direction direction, Player shooter) {
        super(x, y, map, texture);
        this.direction = direction;
        Random r = new Random();
        this.defaultSpeed = r.nextFloat()*2+6;
        this.damage = r.nextInt(15) + 5; 
        this.shooter = shooter;
    }

    public void move() {
        System.out.println("Moviendo");
        if(death) return;
        switch (this.direction){
            case Direction.UP: y = (int) (y - defaultSpeed); return;
            case Direction.DOWN: y = (int) (y + defaultSpeed); return;
            case Direction.RIGHT: x = (int) (x + defaultSpeed); return;
            case Direction.LEFT: x = (int) (x - defaultSpeed); return;
        }
    }

    public void checkDeath(){
        if(!this.canBeIn(x, y)){
            this.death = true;
            this.map.removeProjectile(this);
        }
    }

    public boolean checkCollision(Player player){
        if (player == this.shooter) return false;
        if (this.intersectsWith(player)){
            this.death = true;
            this.map.removeProjectile(this);
            return true;
        }
        return false;
    }

    public int getDamage(){
        return this.damage;
    }
}