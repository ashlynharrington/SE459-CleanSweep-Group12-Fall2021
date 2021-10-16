package main.cleansweep;

import main.tiles.FloorTile;
import main.tiles.FloorTileSet;
import main.tiles.Point;

import java.util.Map;

public class CleanSweepController {
    private CleanSweepStateInterface cleanSweepCommands;
    FloorTileSet floorMap;

    public int maxDirt = 10;
    public int currentDirt = 0;

    public CleanSweepController(FloorTileSet floorPlan){
        cleanSweepCommands = new CleanSweepStateManager(0,0, currentDirt);
        floorMap = floorPlan;
    }

       /*
            example layout is of a 3x3 square room, 9 main.floor tiles

            Up -> increment y
            Down -> decrement y
            Right -> increment x
            Left -> decrement x

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

        int counter = 0;

        while(counter < floorMap.getFloorMapSize() ) {
            while(tryToMoveRight()) {
                System.out.println("Moving Right");
            }

            while(tryToMoveUp()) {
                System.out.println("Moving Up");
            }

            while (tryToMoveLeft()) {
                System.out.println("Moving left");
            }

            while(tryToMoveDown()) {
                System.out.println("Moving down");
            }

            counter++;
        }


//        for(Map.Entry<Point,FloorTile> entry:floorMap.getFloorMap().entrySet()) {
//            FloorTile floorTile = entry.getValue();
//            Point point = entry.getValue().getLocation();
//            System.out.println("Going through Point (" + point.getX() + ", " + point.getY() + ")");
//
//            // eventually should update logic to "isClean" if floorTile.unitsOfDirt = 0 instead of "setVisited"
//            // this will allow the vacuum to "revisit" a tile if it needs more than one pass to clean
//            // right now just checks if the floor tile is dirty, is not an obstacle, and that the dirt isn't full
//              if(floorTile.isDirty() && !isWallOrObstacle(point.getX(),point.getY()) && !floorTile.isObstacle() && currentDirt < maxDirt) {
//                    floorTile.setVisited();
//                    floorTile.removeDirt();
//                    currentDirt+=1;
//                    System.out.println("Removing dirt at (" + point.getX() + ", " + point.getY() + ")");
//            }
//
//        }
//
//        System.out.println("Vacuum has started cleaning");

    }

    //Return boolean stating if moving up one tile succeeded or failed due to obstacle or wall.

    public boolean tryToMoveUp(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()+1)){
            return false;
        }
        cleanSweepCommands.moveUp();
        cleanSweepCommands.getCurrentDirt();
        return true;
    }

    public boolean tryToMoveDown(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()-1)){
            return false;
        }
        cleanSweepCommands.moveDown();
        cleanSweepCommands.getCurrentDirt();
        return true;
    }

    public boolean tryToMoveLeft(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX()-1, cleanSweepCommands.getCurrentY())){
            return false;
        }
        cleanSweepCommands.moveLeft();
        cleanSweepCommands.getCurrentDirt();
        return true;
    }
    public boolean tryToMoveRight(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX()+1, cleanSweepCommands.getCurrentY())){
            return false;
        }
        cleanSweepCommands.moveRight();
        cleanSweepCommands.getCurrentDirt();
        return true;
    }

    private boolean isWallOrObstacle(int x, int y) {
        return (null == floorMap.getFloorTileAt(x, y) || floorMap.getFloorTileAt(x,y).isObstacle());
    }

}
