package com.tatuck.view;

import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TextureManager {
    private static HashMap<Integer, BufferedImage> textures = new HashMap<Integer, BufferedImage>();

    public TextureManager(){
        loadTexture(0, "resources/tiles/grass.png");
    }

    private void loadTexture(int ID, String imagePath){
        try{
            InputStream inputStream = TextureManager.class.getClassLoader().getResourceAsStream(imagePath);
            if (inputStream != null){
                textures.put(ID, ImageIO.read(inputStream));
            }else{
                System.err.println("Imagen \"" + imagePath + "\" no accesible");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static BufferedImage getImage(int ID){
        return textures.get(ID);
    }
}
