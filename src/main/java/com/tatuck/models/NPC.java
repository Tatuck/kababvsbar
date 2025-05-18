package com.tatuck.models;

import java.lang.Runnable;
import java.util.List;

import com.tatuck.controller.Map;
import com.tatuck.view.GamePanel;
import com.tatuck.view.PlayeableTexture;
import com.tatuck.view.Tile;

public class NPC extends PlayeableEntity implements Runnable{
    Player[] players;
    String name;
    public NPC(String name, int[] projectileTexturesID, int x, int y, Map map, PlayeableTexture playeableTexture, Player[] players){
        super(x, y, map, playeableTexture, projectileTexturesID);
        this.players = players;
        this.name = name;
        defaultSpeed = (float)(0.5*defaultSpeed);
        secsPerShoot = 0.5;
    }

    private Player getNearestPlayer(){
        // Get nearest player from NPC
        Player nearestPlayer = null;
        for (int i = 0; i < players.length; i++){
            if (nearestPlayer == null){
                nearestPlayer = players[i];
                continue;
            }
            if (Math.sqrt(Math.pow((this.x-nearestPlayer.x), 2) + Math.pow((this.y-nearestPlayer.y), 2)) > Math.sqrt(Math.pow((this.x-this.players[i].x), 2) + Math.pow((this.y-this.players[i].y), 2))){
                nearestPlayer = this.players[i];
            }
        }
        return nearestPlayer;
    }

    private List<Tile> findPathToPlayer(Player player) {
        return this.map.findShortestPath(this.map.getTileAtScreenCoord(x, y), this.map.getTileAtScreenCoord(player.x, player.y));
    }

    private void moveToPlayer(Player player){
        List<Tile> path = findPathToPlayer(player);
        if (path != null && path.size() > 1) {
            Tile nextTile = path.get(1);

            int currentTileX = this.x / Tile.TILE_SIZE;
            int currentTileY = this.y / Tile.TILE_SIZE;

            if (nextTile.posX > currentTileX) {
                this.move(0, 1); // Right
            } else if (nextTile.posX < currentTileX) {
                this.move(0, -1); // Left
            } else if (nextTile.posY > currentTileY) {
                this.move(-1, 0); // Down
            } else if (nextTile.posY < currentTileY) {
                this.move(1, 0); // Up
            }
        }
    }

    @Override
    public void run(){
        while(this.health > 0){
            Player nearestPlayer = getNearestPlayer();
            moveToPlayer(nearestPlayer);
            tryShoot();
            try{
                Thread.sleep(1000/GamePanel.FPS);
            } catch(InterruptedException e){}
        }
    }

    private void tryShoot(){
        Tile currentTile = map.getTileAtScreenCoord(x, y);
        for(Player player : players){
            Tile playerTile = map.getTileAtScreenCoord(player.x, player.y);
            if(currentTile.posX == playerTile.posX || currentTile.posY == playerTile.posY){
                this.shoot();
            }
        }
    }

    @Override
    public String toString(){
        return this.name;
    }
}
