package main.cleansweep;

import main.tiles.FloorTileSet;

public class CleanSweepController {
    private CleanSweepStateInterface cleanSweepCommands;
    FloorTileSet floorMap;


    public CleanSweepController(FloorTileSet floorPlan){
        cleanSweepCommands = new CleanSweepStateManager(0,0);
        floorMap = floorPlan;
    }

    public void startCleaning(){

    }

    //Return boolean stating if moving up one tile succeeded or failed due to obstacle or wall.

    public boolean tryToMoveUp(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()-1)){
            return false;
        }
        cleanSweepCommands.moveUp();
        return true;
    }

    public boolean tryToMoveDown(){
        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()+1)){
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
        return (null == floorMap.getFloorTileAt(x, y));
    }
}
