package com.tatuck.view;

import java.awt.image.BufferedImage;

public class Texture {
    private BufferedImage image;

    public Texture(int ID) {
        this.image = TextureManager.getInstance().getTexture(ID);
    }

    public BufferedImage getImage() {
        return image;
    }
}