package com.tatuck.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;

import com.tatuck.models.Player;
import com.tatuck.models.Projectile;
import com.tatuck.controller.Map;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
    private Player player1, player2;

    public static final int SCREEN_WIDTH = 1020;//640;
    public static final int SCREEN_HEIGHT = 700; //480;

    private int cameraX, cameraY;
    private HashSet<Integer> pressedKeys;
    private Map map;
    private Timer timer, timerWinner;
    private TextureManager tm;
    private PlayeableTexture player1Texture, player2Texture;
    // TODO: 
    // Poner NPCs que ataquen
    // Poner animaciones cuando das una bala
    // Poner modo 1 jugador
    // Poner varias texturas para las balas
    // Poner rectángulo para ver la vida de los jugadores
    // 

    public GamePanel(){
        tm = TextureManager.getInstance();

        // Create tiles
        tm.loadTexture(0, "resources/tiles/grass.png");
        tm.loadTexture(1, "resources/tiles/fastgrass.png");
        tm.loadTexture(2, "resources/tiles/cubobasura_marron.png");
        tm.loadTexture(3, "resources/tiles/cubobasura_naranja.png");
        tm.loadTexture(4, "resources/tiles/cubobasura_amarillo.png");
        TileManager.createTile(0, true, 3);
        TileManager.createTile(1, true, 10);
        TileManager.createTile(2, false, 1); 
        TileManager.createTile(3, false, 1);
        TileManager.createTile(4, false, 1); 


        // Set up sprites
        tm.loadTexture(200, "resources/sprites/player1/up.png");
        tm.loadTexture(201, "resources/sprites/player1/down.png");
        tm.loadTexture(202, "resources/sprites/player1/right.png");
        tm.loadTexture(203, "resources/sprites/player1/left.png");

        tm.loadTexture(204, "resources/sprites/player2/up.png");
        tm.loadTexture(205, "resources/sprites/player2/down.png");
        tm.loadTexture(206, "resources/sprites/player2/right.png");
        tm.loadTexture(207, "resources/sprites/player2/left.png");

        // Set up items
        tm.loadTexture(300, "resources/items/kebab.png");
        
        player1Texture = new PlayeableTexture(200, 201, 202, 203);
        player2Texture = new PlayeableTexture(204, 205, 206, 207);
    }

    public void reset(){
        this.map = new Map();
        Random r = new Random();
        int player1TileX = r.nextInt(map.getWidth());
        int player1TileY = r.nextInt(map.getHeight());
        int player2TileX = r.nextInt(map.getWidth());
        int player2TileY = r.nextInt(map.getHeight());

        this.player1 = new Player("Jugador 1", player1TileX * Tile.TILE_SIZE, player1TileY * Tile.TILE_SIZE, this.map, this.player1Texture);
        this.player2 = new Player("Jugador 2", player2TileX * Tile.TILE_SIZE, player2TileY * Tile.TILE_SIZE, this.map, this.player2Texture);
        map.putWalkableTile(player1TileX, player1TileY);
        map.putWalkableTile(player2TileX, player2TileY);
        this.pressedKeys = new HashSet<>();
        this.addKeyListener(this);
        this.setFocusable(true);

        // Reset winner timer
        timerWinner = null;

        // Start timer
        timer = new Timer(16, this); // 60 FPS
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics g2d = (Graphics2D) g;
        // Center camera on average of both players
        cameraX = ((player1.x + player2.x)/2) - SCREEN_WIDTH / 2;
        cameraY = ((player1.y + player2.y)/2)  - SCREEN_HEIGHT / 2;
        // Check if camera is outside world limits
        cameraX = Math.max(0, Math.min(cameraX, map.getWidth() * Tile.TILE_SIZE - SCREEN_WIDTH));
        cameraY = Math.max(0, Math.min(cameraY, map.getHeight() * Tile.TILE_SIZE - SCREEN_HEIGHT));

        // Paint tiles
        for(Tile tile : map){
            int screenX = tile.posX * Tile.TILE_SIZE - cameraX;
            int screenY = tile.posY * Tile.TILE_SIZE - cameraY;
            g2d.drawImage(tm.getTexture(tile.tileId), screenX, screenY, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
        }

        // Paint players
        BufferedImage player1Texture = player1.playeableTexture.getCurrentTexture();
        BufferedImage player2Texture = player2.playeableTexture.getCurrentTexture();
        
        g2d.drawImage(player1Texture, player1.x - cameraX, player1.y - cameraY, player1Texture.getWidth(), player1Texture.getHeight(), null);
        g2d.drawImage(player2Texture, player2.x - cameraX, player2.y - cameraY, player2Texture.getWidth(), player2Texture.getHeight(), null);

        // Paint projectiles
        for (Projectile projectile : this.map.getProjectiles()){
            BufferedImage projectileTexture = projectile.getCurrentTexture();
            int screenX = projectile.x - cameraX;
            int screenY = projectile.y - cameraY;
            g2d.drawImage(projectileTexture, screenX, screenY, projectile.getCurrentTexture().getWidth(), projectile.getCurrentTexture().getHeight(), null);
        }

        // Draw health bars
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("JUGADOR 1: " + player1.getHealth(), 20, SCREEN_HEIGHT-50);
        g2d.drawString("JUGADOR 2: " + player2.getHealth(), SCREEN_WIDTH-280, SCREEN_HEIGHT-50);
        
        // Check if there is a winner
        Player winner;
        if ((winner = this.getWinner()) != null){
            Font font = new Font("Arial", Font.BOLD, 100);
            g2d.setFont(font);
            g2d.drawString("GANADOR:", SCREEN_WIDTH/4 - 20, SCREEN_HEIGHT/2 - 60);
            String winnerString = winner + "!";
            FontMetrics fontMetrics = g2d.getFontMetrics(font);
            int textPosX = (SCREEN_WIDTH - fontMetrics.stringWidth(winnerString))/2;
            int textPosY = (SCREEN_HEIGHT - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent();
            g2d.drawString(winnerString, textPosX, textPosY);
            if(timerWinner == null){
                timerWinner = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        GamePanel.this.timer.stop();
                        ChangePanels.getInstance().changePanels("start");
                        GamePanel.this.timerWinner.stop();
                    }
                });
                timerWinner.start();
            }            
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
            if(projectile.checkCollision(player1)) player1.takeDamage(projectile.getDamage());
            if(projectile.checkCollision(player2)) player2.takeDamage(projectile.getDamage());
        }
    }

    private void handleInput(){
        // Player 1
        if(pressedKeys.contains(KeyEvent.VK_W)) player1.move(1, 0);
        else if(pressedKeys.contains(KeyEvent.VK_D)) player1.move(0, 1);
        else if(pressedKeys.contains(KeyEvent.VK_S)) player1.move(-1, 0);
        else if(pressedKeys.contains(KeyEvent.VK_A)) player1.move(0, -1);

        if (pressedKeys.contains(KeyEvent.VK_SPACE)){
            player1.shoot();
        }

        // Player 2
        if(pressedKeys.contains(KeyEvent.VK_UP)) player2.move(1, 0);
        else if(pressedKeys.contains(KeyEvent.VK_RIGHT)) player2.move(0, 1);
        else if(pressedKeys.contains(KeyEvent.VK_DOWN)) player2.move(-1, 0);
        else if(pressedKeys.contains(KeyEvent.VK_LEFT)) player2.move(0, -1);

        if (pressedKeys.contains(KeyEvent.VK_K)){
            player2.shoot();
        }
    }

    private Player getWinner(){
        if(this.player1.getHealth() == 0){
            return this.player2;
        } else if (this.player2.getHealth() == 0){
            return this.player1;
        }
        return null;
    }

}
