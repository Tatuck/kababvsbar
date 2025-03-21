package com.tatuck.view;

public class TileProperties{
    private int ID;
    private boolean walkable;
    private float speedMultiplier = 0.0f;

    TileProperties(int ID, boolean walkable, float speedMultiplier){
        this.ID = ID;
        this.walkable = walkable;
        this.speedMultiplier = speedMultiplier;
    }

    public int getID(){
        return this.ID;
    }
    public boolean getWalkable(){
        return this.walkable;
    }
    public float getSpeedMultiplier(){
        return this.speedMultiplier;
    }
}