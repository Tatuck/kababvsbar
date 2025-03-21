package com.tatuck.view;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.io.InputStream;

public class TextureManager {
    private static TextureManager instance = null;
    private HashMap<Integer, BufferedImage> textures;

    // Singleton pattern
    private TextureManager() {
        textures = new HashMap<>();
    }

    public static TextureManager getInstance() {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    public void loadTexture(int ID, String path) {
        BufferedImage image = loadImage(path);
        if (image != null) {
            textures.put(ID, image);
        }
    }

    public BufferedImage getTexture(int ID) {
        return textures.get(ID);
    }

    private BufferedImage loadImage(String path) {
        try {
            InputStream inputStream = TextureManager.class.getClassLoader().getResourceAsStream(path);
            if (inputStream != null) {
                return ImageIO.read(inputStream);
            } else {
                System.err.println("Image not found: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}