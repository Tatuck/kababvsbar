package com.tatuck.models;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.tatuck.controller.Map;
import com.tatuck.view.PlayeableTexture.Direction;

public class Projectile extends Entity {
    public int damage = 10;
    public boolean death = false;
    public Direction direction;

    public Projectile(int x, int y, Map map, BufferedImage texture, Direction direction) {
        super(x, y, map, texture);
        this.direction = direction;
        Random r = new Random();
        this.defaultSpeed = r.nextFloat()*4+1;
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
}