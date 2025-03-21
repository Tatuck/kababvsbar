package com.tatuck.view;

import java.util.HashMap;

public class TileManager {
    private static HashMap<Integer, TileProperties> tiles = new HashMap<Integer, TileProperties>();

    public void createTile(int ID, boolean walkable, float speedMultiplier){
        tiles.put(ID, new TileProperties(ID, walkable, speedMultiplier));
    }

    public static TileProperties getTilePropertiesFromID(int ID){
        return tiles.get(ID);
    }
}
