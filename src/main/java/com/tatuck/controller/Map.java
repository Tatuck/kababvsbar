package com.tatuck.controller;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import com.tatuck.view.GamePanel;
import com.tatuck.view.Tile;
import com.tatuck.models.Projectile;

public class Map implements Iterable<Tile>{
    private int TILE_SIZE = Tile.TILE_SIZE;
    private Tile[][] tileMap;
    private int height, width = 0;
    public ArrayList<Projectile> projectiles;

    // A* algorithm
    private class PathNode implements Comparable<PathNode> {
        Tile tile;
        double gScore; // Costo real desde el inicio hasta este nodo
        double fScore; // Costo estimado total desde el inicio hasta el objetivo (gScore + h)

        PathNode(Tile tile, double gScore, double fScore) {
            this.tile = tile;
            this.gScore = gScore;
            this.fScore = fScore;
        }

        @Override
        public int compareTo(PathNode other) {
            return Double.compare(this.fScore, other.fScore);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PathNode pathNode = (PathNode) o;
            return tile.equals(pathNode.tile);
        }

        @Override
        public int hashCode() {
            return tile.hashCode();
        }
    }

    public Map(String mapFilePath){
        this.initialize();
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

            this.tileMap = new Tile[height][width];

            scanner = new Scanner(Map.class.getClassLoader().getResourceAsStream(mapFilePath));

            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tileIds = line.split(",");
                for (int col = 0; col < tileIds.length; col++) {
                    int tileId = Integer.parseInt(tileIds[col]);
                    tileMap[row][col] = new Tile(tileId, col, row);
                }
                row++;
            }
            scanner.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private Tile getRandomTile(int x, int y){
        return this.getRandomTile(x, y, false);
    }

    private Tile getRandomTile(int x, int y, boolean forceWalkable){
        Random r = new Random();
        if(forceWalkable || r.nextInt(100) < 90){
            if(r.nextInt(100)<75){
                return new Tile(0, x, y);
            }else{
                return new Tile(1, x, y);
            }
        } else{
            return new Tile(r.nextInt(3) + 2, x, y);
        }
    }

    public Map(){
        this.initialize();
        // create random map
        Random r = new Random();
        this.width = GamePanel.SCREEN_WIDTH/this.TILE_SIZE * (100 + r.nextInt(30))/100;
        this.height = GamePanel.SCREEN_HEIGHT/this.TILE_SIZE * (100 + r.nextInt(20))/100;
        this.tileMap = new Tile[height][width];
        for(int x = 0; x < width; x ++){
            for(int y = 0; y < height; y ++){
                this.tileMap[y][x] = this.getRandomTile(x, y);
            }
        }
    }

    private void initialize(){
        this.projectiles = new ArrayList<>();
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile){
        this.projectiles.remove(projectile);
    }

    public ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }

    public Tile getTileAtScreenCoord(int x, int y){
        if (x < 0 || y < 0) return null;
        int tileX = x / TILE_SIZE;
        int tileY = y / TILE_SIZE;
        return getTileAtCoord(tileX, tileY);
    }

    public Tile getTileAtCoord(int x, int y){
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tileMap[y][x];
        }
        return null;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void putWalkableTile(int x, int y){
        this.tileMap[y][x] = this.getRandomTile(x, y, true);
    }

    public ArrayList<Tile> getTileNeighbors(Tile tile) {
        ArrayList<Tile> neighbors = new ArrayList<>();
        Tile up = getTileAtCoord(tile.posX, tile.posY - 1);
        Tile down = getTileAtCoord(tile.posX, tile.posY + 1);
        Tile right = getTileAtCoord(tile.posX + 1, tile.posY);
        Tile left = getTileAtCoord(tile.posX - 1, tile.posY);
        if (up != null && up.properties.getWalkable()) neighbors.add(up);
        if (down != null && down.properties.getWalkable()) neighbors.add(down);
        if (right != null && right.properties.getWalkable()) neighbors.add(right);
        if (left != null && left.properties.getWalkable()) neighbors.add(left);
        return neighbors;
    }

    private double heuristic(Tile tile1, Tile tile2) {
        return Math.abs(tile1.posX - tile2.posX) + Math.abs(tile1.posY - tile2.posY);
    }

    // Esto es una locura de implementar, un saludo :)
    // Además, no funciona del todo bien, pero bueno.
    // https://www.geeksforgeeks.org/a-search-algorithm/
    public List<Tile> findShortestPath(Tile startTile, Tile goalTile) {
        if (startTile == null || goalTile == null){ // || !startTile.properties.getWalkable() || !goalTile.properties.getWalkable()) {
            return null;
        }
        
        if (startTile.equals(goalTile)) {
            List<Tile> path = new ArrayList<>();
            path.add(startTile);
            return path;
        }


        PriorityQueue<PathNode> openList = new PriorityQueue<>();
        HashSet<Tile> closedList = new HashSet<>();
        HashMap<Tile, Double> gScore = new HashMap<>();
        HashMap<Tile, Tile> cameFrom = new HashMap<>();

        gScore.put(startTile, 0.0);
        double initialFScore = heuristic(startTile, goalTile);

        PathNode startNode = new PathNode(startTile, 0.0, initialFScore);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            PathNode currentNode = openList.poll();

            if (closedList.contains(currentNode.tile)) {
                continue;
            }

            if (currentNode.tile.equals(goalTile)) {
                return reconstructPath(cameFrom, goalTile);
            }

            closedList.add(currentNode.tile);


            List<Tile> neighbors = getTileNeighbors(currentNode.tile);
            for (Tile neighborTile : neighbors) {
                if (closedList.contains(neighborTile)) {
                    continue;
                }

                if (!neighborTile.properties.getWalkable()) {
                    continue;
                }

                double tentativeGScore = gScore.get(currentNode.tile) + 1.0;

                if (!gScore.containsKey(neighborTile) || tentativeGScore < gScore.get(neighborTile)) {

                    cameFrom.put(neighborTile, currentNode.tile);
                    gScore.put(neighborTile, tentativeGScore);
                    double neighborFScore = tentativeGScore + heuristic(neighborTile, goalTile);

                    PathNode neighborNode = new PathNode(neighborTile, tentativeGScore, neighborFScore);
                    openList.add(neighborNode);
                }
            }
        }

        return null;
    }

    private List<Tile> reconstructPath(HashMap<Tile, Tile> cameFrom, Tile currentTile) {
        List<Tile> totalPath = new ArrayList<>();
        totalPath.add(currentTile);
        while (cameFrom.containsKey(currentTile)) {
            currentTile = cameFrom.get(currentTile);
            totalPath.add(currentTile);
        }
        Collections.reverse(totalPath);
        return totalPath;
    }


    @Override
    public Iterator<Tile> iterator() {
        return new Iterator<Tile>() {
            private int currentRow = 0;
            private int currentCol = 0;

            @Override
            public boolean hasNext() {
                return currentRow < height && (currentRow < height - 1 || currentCol < width);
            }

            @Override
            public Tile next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Tile tile = tileMap[currentRow][currentCol];
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