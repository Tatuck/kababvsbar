package com.tatuck.view;

public class LiveTile extends Tile {
    public int health = 100;
    LiveTile(int ID, int posX, int posY){
        super(ID, posX, posY);
    }

    public void hit(int damage){
        this.health = Math.max(0, health-damage);
    }
}
