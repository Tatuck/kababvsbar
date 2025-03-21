package com.tatuck.view;

public class Tile {
    public static final int TILE_SIZE = 32;
    
    public int posX, posY;
    public int tileId;
    public TileProperties properties;

    public Tile(int ID, int posX, int posY){
        this.properties = TileManager.getTilePropertiesFromID(ID);
        this.tileId = ID;
        this.posX = posX;
        this.posY = posY;
    }

    public void setPos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public String toString(){
        return "X:"+this.posX+ " Y:"+this.posY + " ID:"+this.tileId;
    }
}
