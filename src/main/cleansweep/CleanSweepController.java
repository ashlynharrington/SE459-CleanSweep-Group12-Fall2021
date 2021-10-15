package main.cleansweep;

import main.tiles.FloorTile;
import main.tiles.FloorTileSet;
import main.tiles.Point;

import java.util.Map;

public class CleanSweepController {
    private CleanSweepStateInterface cleanSweepCommands;
    FloorTileSet floorMap;


    public CleanSweepController(FloorTileSet floorPlan){
        cleanSweepCommands = new CleanSweepStateManager(0,0);
        floorMap = floorPlan;
    }

       /*
            example layout is of a 3x3 square room, 9 main.floor tiles
            a (0,0)     b (0,1)      c (0,2)
            d-4 (1,0)   e-(1,1)      f-6 (1,2)
            g-7 (2,0)   h-8 (2,1)    i-9 (2,2)
         */

    public void startCleaning(){
        // point -> (0,1)

        /*
            1. Loop through key set
            2. Check if the point is in the tileset
                -> clean it
                -> set to visited
            3. Move to another tile that hasn't been cleaned ()
         */




        for(Map.Entry<Point,FloorTile> entry:floorMap.getFloorMap().entrySet()) {
            FloorTile floorTile = entry.getValue();
            Point point = entry.getValue().getLocation();
            System.out.println("Going through Point (" + point.getX() + ", " + point.getY() + ")");
            if(floorTile.isDirty() && !isWallOrObstacle(point.getX(),point.getY()) && !floorTile.isObstacle()) {
                    floorTile.setVisited();
                    floorTile.removeDirt();
                    //System.out.println("Removing dirt at (" + point.getX() + ", " + point.getY() + ")");
            }
        }
        System.out.println("Vaccum has started cleaning");
    }

    //Return boolean stating if moving up one tile succeeded or failed due to obstacle or wall.

    public boolean tryToMoveUp(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX()-1, cleanSweepCommands.getCurrentY()-1)){
            return false;
        }
        cleanSweepCommands.moveUp();
        return true;
    }

    public boolean tryToMoveDown(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX()+1, cleanSweepCommands.getCurrentY())){
            return false;
        }
        cleanSweepCommands.moveDown();
        return true;
    }

    public boolean tryToMoveLeft(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()-1)){
            return false;
        }
        cleanSweepCommands.moveLeft();
        return true;
    }
    public boolean tryToMoveRight(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY())){
            return false;
        }
        cleanSweepCommands.moveRight();
        return true;
    }

    private boolean isWallOrObstacle(int x, int y) {
        return (null == floorMap.getFloorTileAt(x, y));
    }
}
