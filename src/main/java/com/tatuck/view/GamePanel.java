package com.tatuck.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import javax.swing.Timer;
import java.util.ArrayList;

import com.tatuck.models.Player;
import com.tatuck.models.Projectile;
import com.tatuck.controller.Map;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
    private final Player player;

    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;

    private int cameraX, cameraY;
    private HashSet<Integer> pressedKeys;
    private Map map;
    private final Timer timer;
    private TextureManager tm;

    private double lastProjectileShot;

    public GamePanel(){
        tm = TextureManager.getInstance();

        // Create tiles
        tm.loadTexture(0, "resources/tiles/grass.png");
        TileManager.createTile(0, true, 3);
        TileManager.createTile(1, false, 1); 
        TileManager.createTile(2, false, 1);
        TileManager.createTile(3, false, 1); 


        // Set up sprites
        tm.loadTexture(200, "resources/sprites/player/up.png");
        tm.loadTexture(201, "resources/sprites/player/down.png");
        tm.loadTexture(202, "resources/sprites/player/right.png");
        tm.loadTexture(203, "resources/sprites/player/left.png");

        // Set up items
        tm.loadTexture(300, "resources/items/kebab.png");
        
        PlayeableTexture playerTexture = new PlayeableTexture(200, 201, 202, 203);
        this.map = new Map("resources/map.txt");
        this.player = new Player(2, 0, this.map, playerTexture);
        this.pressedKeys = new HashSet<>();
        this.addKeyListener(this);
        this.setFocusable(true);

        this.lastProjectileShot = 0.0;

        // Start timer
        timer = new Timer(16, this); // 60 FPS
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics g2d = (Graphics2D) g;
        // Center camera on player
        cameraX = player.x - SCREEN_WIDTH / 2;
        cameraY = player.y - SCREEN_HEIGHT / 2;
        // Check if camera is outside world limits
        cameraX = Math.max(0, Math.min(cameraX, map.getWidth() * Tile.TILE_SIZE - SCREEN_WIDTH));
        cameraY = Math.max(0, Math.min(cameraY, map.getHeight() * Tile.TILE_SIZE - SCREEN_HEIGHT));

        // Paint tiles
        for(Tile tile : map){
            int screenX = tile.posX * Tile.TILE_SIZE - cameraX;
            int screenY = tile.posY * Tile.TILE_SIZE - cameraY;
            g2d.drawImage(tm.getTexture(tile.tileId), screenX, screenY, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
        }

        // Paint player
        BufferedImage playerTexture = player.playeableTexture.getCurrentTexture();
        
        int playerPosX = player.x - cameraX;
        int playerPosY = player.y - cameraY;
        g2d.drawImage(playerTexture, playerPosX, playerPosY, playerTexture.getWidth(), playerTexture.getHeight(), null);

        // Paint projectiles
        for (Projectile projectile : this.map.getProjectiles()){
            BufferedImage projectileTexture = projectile.getCurrentTexture();
            int screenX = projectile.x - cameraX;
            int screenY = projectile.y - cameraY;
            g2d.drawImage(projectileTexture, screenX, screenY, 32, 32, null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e){
        pressedKeys.remove(Integer.valueOf(e.getKeyCode()));
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void actionPerformed(ActionEvent e){
        handleInput();
        moveProjectiles();
        repaint();
    }

    private void moveProjectiles(){
        ArrayList<Projectile> projectiles = new ArrayList<>(this.map.getProjectiles());
        for (Projectile projectile : projectiles){
            projectile.move();
            projectile.checkDeath();
        }
        this.lastProjectileShot = Math.max(0, this.lastProjectileShot - 0.1);
    }

    private void handleInput(){
        if(pressedKeys.contains(KeyEvent.VK_UP)) player.move(1, 0);
        else if(pressedKeys.contains(KeyEvent.VK_RIGHT)) player.move(0, 1);
        else if(pressedKeys.contains(KeyEvent.VK_DOWN)) player.move(-1, 0);
        else if(pressedKeys.contains(KeyEvent.VK_LEFT)) player.move(0, -1);

        if (pressedKeys.contains(KeyEvent.VK_SPACE) && this.lastProjectileShot == 0.0){
            player.shoot();
            this.lastProjectileShot = 1.0;
        }
    }

}
