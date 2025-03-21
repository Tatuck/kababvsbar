package com.tatuck.controller;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.tatuck.view.Tile;

public class Map implements Iterable<Tile>{
    private int TILE_SIZE = Tile.TILE_SIZE;
    private Tile[][] tileMap;
    private int height, width = 0;

    public Map(String mapFilePath){
        try {
            InputStream inputStream = Map.class.getClassLoader().getResourceAsStream(mapFilePath);
            
            if (inputStream == null){
                System.out.println("Mapa no encontrado");
                return;
            }


            // Set dimensions of the map
            Scanner scanner = new Scanner(inputStream);

            height = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tileIds = line.split(",");
                width = tileIds.length;
                height++;
            }

            tileMap = new Tile[width][height];

            scanner = new Scanner(Map.class.getClassLoader().getResourceAsStream(mapFilePath));

            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tileIds = line.split(",");
                for (int col = 0; col < tileIds.length; col++) {
                    int tileId = Integer.parseInt(tileIds[col]);
                    tileMap[col][row] = new Tile(tileId, row, col);
                }
                row++;
            }
            scanner.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public Tile getTileAtScreenCoord(int x, int y){
        int tileX = x / TILE_SIZE;
        int tileY = y / TILE_SIZE;
        return getTileAtCoord(tileX, tileY);
    }

    public Tile getTileAtCoord(int x, int y){
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tileMap[x][y];
        }
        throw new IndexOutOfBoundsException("Tile position out of map bounds.");
    }

    @Override
    public Iterator<Tile> iterator() {
        return new Iterator<Tile>() {
            private int currentRow = 0;
            private int currentCol = 0;

            @Override
            public boolean hasNext() {
                return currentRow < height && currentCol < width;
            }

            @Override
            public Tile next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Tile tile = tileMap[currentCol][currentRow];
                currentCol++;
                if (currentCol >= width) {
                    currentCol = 0;
                    currentRow++;
                }
                return tile;
            }
        };
    }
}
