package main.cleansweep;

import main.tiles.FloorTileSet;
import main.tiles.FloorTile;
import main.tiles.FloorTileType;

public class CleanSweepController {
    private static final int MAX_BATTERY_CAPACITY = 30;
    private static final int MAX_DIRT_CAPACITY = 10;

    private CleanSweepStateInterface cleanSweepCommands;
    FloorTileSet floorMap;

    public int currentVacuumDirt = 0;
    private double unitsOfCharge = 20.0;

    public CleanSweepController(FloorTileSet floorPlan){
        cleanSweepCommands = new CleanSweepStateManager(1,1);
        floorMap = floorPlan;
    }

       /*
            example layout is of a 3x3 square room, 9 main.floor tiles

            Up -> increment y
            Down -> decrement y
            Right -> increment x
            Left -> decrement x

         */

    public void startCleaningCycle(){
        // point -> (0,1)

        /*
            1. Check battery level, if not full, charge to full battery
            2. While dirt capacity is not full and battery is not dead
                -> try to move to another tile
                -> try to clean that tile
         */

        //check battery level at start of cycle
        tryToChargeBattery();

        while(checkBattery() && (getCurrentVacuumDirt() < MAX_DIRT_CAPACITY)) {


            while(tryToMoveRight()) {
                tryToClean();
            }

            while(tryToMoveUp()) {
                tryToClean();
            }

            while (tryToMoveLeft()) {
                tryToClean();
            }

            while(tryToMoveDown()) {
                tryToClean();
            }


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
        if(!checkBattery()){
            return false;
        }

        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()+1)) {
            return false;
        }
        FloorTileType currentFloorTileType =  getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType =  floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY() + 1).getType();

        //if not enough battery left, operation fails, battery dead
        if(!reduceChargeOnMove(currentFloorTileType, nextFloorTileType)){
            return false;
        }

        System.out.println("   Moving up");
        cleanSweepCommands.moveUp();

        return true;
    }

    public boolean tryToMoveDown(){
        if(!checkBattery()){
            return false;
        }

        if(isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()-1)){
            return false;
        }
        FloorTileType currentFloorTileType = getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType =  floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY() - 1).getType();

        //if not enough battery left, operation fails, battery dead
        if(!reduceChargeOnMove(currentFloorTileType, nextFloorTileType)){
            return false;
        }

        System.out.println("   Moving down");
        cleanSweepCommands.moveDown();

        return true;
    }

    public boolean tryToMoveLeft(){
        if(!checkBattery()){
            return false;
        }

        if(isWallOrObstacle(cleanSweepCommands.getCurrentX()-1, cleanSweepCommands.getCurrentY())){
            return false;
        }
        FloorTileType currentFloorTileType =  getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType =  floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX() - 1, cleanSweepCommands.getCurrentY()).getType();

        //if not enough battery left, operation fails, battery dead
        if(!reduceChargeOnMove(currentFloorTileType, nextFloorTileType)){
            return false;
        }

        System.out.println("   Moving left");
        cleanSweepCommands.moveLeft();

        return true;
    }
    public boolean tryToMoveRight(){
        if(!checkBattery()){
            return false;
        }

        if(isWallOrObstacle(cleanSweepCommands.getCurrentX()+1, cleanSweepCommands.getCurrentY())){
            return false;
        }
        FloorTileType currentFloorTileType =  getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType =  floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX() + 1, cleanSweepCommands.getCurrentY()).getType();

        if(!reduceChargeOnMove(currentFloorTileType, nextFloorTileType)){
            return false;
        }

        System.out.println("   Moving right");
        cleanSweepCommands.moveRight();

        return true;
    }

    private boolean isWallOrObstacle(int x, int y) {
        return (null == floorMap.getFloorTileAt(x, y) || floorMap.getFloorTileAt(x,y).isObstacle());

    }

    private boolean reduceChargeOnClean(FloorTileType floorTileType){
        unitsOfCharge -= floorTileType.getValue();
        System.out.println("   Reduced charge from cleaning " + floorTileType + ": -" + floorTileType.getValue());
        System.out.println("   Current battery level: " + getCurrentBatteryLevel());
        if(getCurrentBatteryLevel() > 0){
            return true;
        } else {
            System.out.println("\nBattery dead");
            return false;
        }
    }

    private boolean reduceChargeOnMove(FloorTileType currentTile, FloorTileType nextTile){
        double batteryCostToMove = (double)(currentTile.getValue() + nextTile.getValue())/2;
        unitsOfCharge -= batteryCostToMove;
        System.out.println("   Reduced charge from moving from " + currentTile + " to " +nextTile + " -" + batteryCostToMove);
        System.out.println("   Current battery level: " + getCurrentBatteryLevel());
        if(getCurrentBatteryLevel() > 0){
            return true;
        } else {
            System.out.println("\nBattery dead");
            return false;
        }
    }

    public void tryToChargeBattery(){
        if(getCurrentFloorTile().isChargingStation()){
            unitsOfCharge = MAX_BATTERY_CAPACITY;
        }
        System.out.println("   Charged battery to: " + getCurrentBatteryLevel());
    }

    public boolean checkBattery(){
        return (unitsOfCharge > 0);
    }

    public double getCurrentBatteryLevel(){
        return unitsOfCharge;
    }

    public int getCurrentVacuumDirt(){
        return currentVacuumDirt;
    }

    public FloorTile getCurrentFloorTile(){
        return floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY());
    }

    private boolean tryToClean() {
        if(!checkBattery()){
            return false;
        }

        FloorTile floorTile = getCurrentFloorTile();

        // if current dirt is less than max dirt and the tile is dirty, remove dirt
        if(currentVacuumDirt < MAX_DIRT_CAPACITY && floorTile.isDirty()) {
            floorTile.removeDirt();
            currentVacuumDirt+=1;
            System.out.println("   Tile dirty, dirt added.");
            System.out.println("   Current vacuum dirt level: " + currentVacuumDirt);

            //if we are able to clean, battery charge will be reduced based on flooring type
            //if not enough battery left, operation fails, battery dead
            if(!reduceChargeOnClean(floorTile.getType())){
                return false;
            }

            return true;
        }
        // if current dirt is less than max dirt and the tile is not dirty, do not remove dirt
        else if(currentVacuumDirt < MAX_DIRT_CAPACITY && !floorTile.isDirty()) {
            System.out.println("   Tile not dirty, no dirt added.");
            System.out.println("   Current vacuum dirt level: " + currentVacuumDirt);
            return false;
        }
        // if current dirt is more than max dirt do not remove dirt
        else if(currentVacuumDirt >= MAX_DIRT_CAPACITY) {
            System.out.println("   Current vacuum dirt level: " + currentVacuumDirt);
            System.out.println("   Vacuum full. No longer cleaning.");
            return false;
        };
        return true;
    };

}
