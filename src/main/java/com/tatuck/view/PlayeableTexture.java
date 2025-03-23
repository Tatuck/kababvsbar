package com.tatuck.view;

import java.awt.image.BufferedImage;

public class PlayeableTexture {
    private BufferedImage upTexture;
    private BufferedImage downTexture;
    private BufferedImage rightTexture;
    private BufferedImage leftTexture;
    private Direction currentDirection;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public PlayeableTexture(int up, int down, int right, int left) {
        TextureManager tm = TextureManager.getInstance();
        this.upTexture = tm.getTexture(up);
        this.downTexture = tm.getTexture(down);
        this.rightTexture = tm.getTexture(right);
        this.leftTexture = tm.getTexture(left);
        this.currentDirection = Direction.DOWN;
    }

    public BufferedImage getCurrentTexture() {
        switch (currentDirection) {
            case UP: return upTexture;
            case DOWN: return downTexture;
            case RIGHT: return rightTexture;
            case LEFT: return leftTexture;
            default: return downTexture;
        }
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public Direction getDirection(){
        return this.currentDirection;
    }
}