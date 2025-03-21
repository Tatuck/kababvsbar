package com.tatuck.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.Timer;

import com.tatuck.models.Player;
import com.tatuck.controller.Map;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
    private final Player player;

    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;

    private int cameraX, cameraY;
    private ArrayList<Integer> pressedKeys;
    private Map map;
    private final Timer timer;

    public GamePanel(){
        new TextureManager();
        this.map = new Map("resources/map.txt");
        this.player = new Player(40, 40, this.map);
        this.pressedKeys = new ArrayList<>();
        this.addKeyListener(this);

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
        cameraX = Math.max(0, Math.min(cameraX, Tile.TILE_SIZE - SCREEN_WIDTH));
        cameraY = Math.max(0, Math.min(cameraY, Tile.TILE_SIZE - SCREEN_HEIGHT));

        for(Tile tile : map){
            int screenX = tile.posX * Tile.TILE_SIZE - cameraX;
            int screenY = tile.posY * Tile.TILE_SIZE - cameraY;
            g2d.drawImage(TextureManager.getImage(tile.tileId), screenX, screenY, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e){
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void actionPerformed(ActionEvent e){
        handleInput();
        repaint();
    }

    private void handleInput(){
        if(pressedKeys.contains(KeyEvent.VK_UP)) player.move(1, 0);
        if(pressedKeys.contains(KeyEvent.VK_RIGHT)) player.move(0, 1);
        if(pressedKeys.contains(KeyEvent.VK_DOWN)) player.move(-1, 0);
        if(pressedKeys.contains(KeyEvent.VK_LEFT)) player.move(0, -1);
    }

}
