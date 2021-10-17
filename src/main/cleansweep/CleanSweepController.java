package main.cleansweep;

import main.tiles.FloorTileSet;
import main.tiles.FloorTile;

public class CleanSweepController {
    private CleanSweepStateInterface cleanSweepCommands;
    FloorTileSet floorMap;

    public int maxVacuumDirt = 10;
    public int currentVacuumDirt = 0;

    public CleanSweepController(FloorTileSet floorPlan){
        cleanSweepCommands = new CleanSweepStateManager(0,0);
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
                if(tryToClean(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY(), currentVacuumDirt, maxVacuumDirt)) {
                    currentVacuumDirt+=1;
                };
            }

            while(tryToMoveUp()) {
                System.out.println("Moving Up");
                if(tryToClean(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY(), currentVacuumDirt, maxVacuumDirt)) {
                    currentVacuumDirt+=1;
                };
            }

            while (tryToMoveLeft()) {
                System.out.println("Moving left");
                if(tryToClean(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY(), currentVacuumDirt, maxVacuumDirt)) {
                    currentVacuumDirt+=1;
                };
            }

            while(tryToMoveDown()) {
                System.out.println("Moving down");
                if(tryToClean(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY(), currentVacuumDirt, maxVacuumDirt)) {
                    currentVacuumDirt+=1;
                };
            }
            counter++;
        }

//        for(Map.Entry<Point,FloorTile> entry:floorMap.getFloorMap().entrySet()) {
//            FloorTile floorTile = entry.getValue();
//            Point point = entry.getValue().getLocation();
//            System.out.println("Going through Point (" + point.getX() + ", " + point.getY() + ")");

            // eventually should update logic to "isClean" if floorTile.unitsOfDirt = 0 instead of "setVisited"
            // this will allow the vacuum to "revisit" a tile if it needs more than one pass to clean
            // right now just checks if the floor tile is dirty, is not an obstacle, and that the dirt isn't full
//              if(floorTile.isDirty() && !isWallOrObstacle(point.getX(),point.getY()) && !floorTile.isObstacle() && currentDirt < maxDirt) {
//                    floorTile.setVisited();
//                    floorTile.removeDirt();
//                    currentDirt+=1;
//                    System.out.println("Removing dirt at (" + point.getX() + ", " + point.getY() + ")");
//                  System.out.println("Current dirt level is: " + currentDirt);
//            }

//        }
//
//        System.out.println("Vacuum has started cleaning");

    }

    //Return boolean stating if moving up one tile succeeded or failed due to obstacle or wall.

    public boolean tryToMoveUp(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()+1)) {
            return false;
        }
        cleanSweepCommands.moveUp();
        return true;
    }

    public boolean tryToMoveDown(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()-1)){
            return false;
        }
        cleanSweepCommands.moveDown();
        return true;
    }

    public boolean tryToMoveLeft(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX()-1, cleanSweepCommands.getCurrentY())){
            return false;
        }
        cleanSweepCommands.moveLeft();
        return true;
    }
    public boolean tryToMoveRight(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX()+1, cleanSweepCommands.getCurrentY())){
            return false;
        }
        cleanSweepCommands.moveRight();
        return true;
    }

    private boolean isWallOrObstacle(int x, int y) {
        return (null == floorMap.getFloorTileAt(x, y) || floorMap.getFloorTileAt(x,y).isObstacle());
    }

    private boolean tryToClean(int x, int y, int currentVacuumDirt, int maxVacuumDirt) {
        FloorTile floorTile = floorMap.getFloorTileAt(x, y);

        // if current dirt is less than max dirt and the tile is dirty, remove dirt
        if(currentVacuumDirt < maxVacuumDirt && floorMap.getFloorTileAt(x, y).isDirty()) {
            floorTile.removeDirt();
            System.out.println("Tile dirty, dirt added.");
            System.out.println("Current vacuum dirt level: " + currentVacuumDirt);
            return true;
        }
        // if current dirt is less than max dirt and the tile is not dirty, do not remove dirt
        else if(currentVacuumDirt < maxVacuumDirt && !floorMap.getFloorTileAt(x, y).isDirty()) {
            System.out.println("Tile not dirty, no dirt added.");
            System.out.println("Current vacuum dirt level: " + currentVacuumDirt);
            return false;
        }
        // if current dirt is more than max dirt do not remove dirt
        else if(currentVacuumDirt >= maxVacuumDirt) {
            System.out.println("Current vacuum dirt level: " + currentVacuumDirt);
            System.out.println("Vacuum full. No longer cleaning.");
            return false;
        };
        return true;
    };

}
