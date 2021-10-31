package main.cleansweep;

import main.pathfinding.Path;
import main.pathfinding.PathFinder;
import main.tiles.FloorTileSet;
import main.tiles.FloorTile;
import main.tiles.FloorTileType;
import main.tiles.Point;

import java.util.*;

public class CleanSweepController {
    Path heuristic = null;
    private static final int MAX_BATTERY_CAPACITY = 100;
    private static final int MAX_DIRT_CAPACITY = 100;

    private CleanSweepStateInterface cleanSweepCommands;
    FloorTileSet floorMap;

    //list to hold coordinates when CleanSweep observes tiles around it
    //coordinates of unvisited tiles are saved at the point the CleanSweep "sees" them
    private final Set<Point> sensedUnvisitedPoints;
    private final Set<Point> visitedPoints;


    public int currentVacuumDirt = 0;
    private double unitsOfCharge = 20.0;

    public CleanSweepController(FloorTileSet floorPlan) {
        cleanSweepCommands = new CleanSweepStateManager(1, 1);
        floorMap = floorPlan;
        sensedUnvisitedPoints = new HashSet<>();
        visitedPoints = new HashSet<>();
    }


    private ArrayList<Point> senseAdjacentTiles() {
        ArrayList<Point> potentialMoves = new ArrayList<>();

        int currentY = cleanSweepCommands.getCurrentY();
        int currentX = cleanSweepCommands.getCurrentX();

        Point up = new Point(currentX, currentY + 1);
        Point down = new Point(currentX, currentY - 1);
        Point right = new Point(currentX + 1, currentY);
        Point left = new Point(currentX - 1, currentY);

        //potential moves are any adjacent tiles that are not obstacles or not null
        //If a tile is visited the CleanSweep could still move there
        if (!isWallOrObstacle(up)) potentialMoves.add(up);
        if (!isWallOrObstacle(down)) potentialMoves.add(down);
        if (!isWallOrObstacle(right)) potentialMoves.add(right);
        if (!isWallOrObstacle(left)) potentialMoves.add(left);

        //Of all potential moves, save the unvisited tiles
        potentialMoves.forEach(p -> {
            if (!isVisited(p)) sensedUnvisitedPoints.add(p);
        });

        System.out.println("   Potential Moves: " + potentialMoves);
        return potentialMoves;
    }

    private Point pickPointToMoveTo(ArrayList<main.tiles.Point> potentialMoves) {
        Point move = null;

        //if only one move is available, move there
        if (potentialMoves.size() == 1) {
            move = potentialMoves.get(0);
            return move;
        }

        //try to move to an unvisited tile first
        for (Point p : potentialMoves) {
            if (!floorMap.getFloorTileAt(p).isVisited()) {
                move = new Point(p.getX(), p.getY());
                break;
            }
        }

        System.out.println("   Sensed Unvisited Tiles List: " + sensedUnvisitedPoints.toString());

        //if no unvisited tiles to move to, try to go toward an unvisited tile
        //do not go back to the previous tile

        /**
        if (move == null) {
            Point mostRecentUnvisitedPointSeen = potentialMoves.get(potentialMoves.size() - 1);
            double shortestDistance = mostRecentUnvisitedPointSeen.distanceToPoint(new Point(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()));

            for (Point p : potentialMoves) {
                if (p.distanceToPoint(mostRecentUnvisitedPointSeen) < shortestDistance) {
                    move = p;
                }
            }
        }
         */

        //Need to address if obstacle between CleanSweep and unvisited tile
        if (move == null) {
            Point sensedButUnvisitedPoint = null;
            Object[] arrayOfUnvisited = sensedUnvisitedPoints.toArray();
            if(arrayOfUnvisited.length == 0){
                System.out.println("Visited everywhere");
                move = new Point(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY());
            }else {
               sensedButUnvisitedPoint  = (Point)arrayOfUnvisited[0];
            }
            PathFinder pf = new PathFinder(
                    new Point(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()),
                    sensedButUnvisitedPoint,
                    floorMap);
            heuristic = pf.findPath();
            if(heuristic!= null) {
                heuristic.printPath();
                move = heuristic.getNextMove();
            }
        }

        return move;
    }

    private void removePointFromUnvisitedList(Point p) {
        for (Point point : sensedUnvisitedPoints) {
            if (point.equals(p)) {
                sensedUnvisitedPoints.remove(point);
                visitedPoints.add(point);
                System.out.println("   Removed Point from unvisited list: " + point.toString());
                System.out.println("   Added Point to visited list: " + point.toString());
                System.out.println("   Tiles visited: " + visitedPoints);
                break;
            }
        }
    }

    private boolean tryToMove() {
        //first the CleanSweep looks around at adjacent tiles for potential moves
        ArrayList<main.tiles.Point> potentialMoves = senseAdjacentTiles();

        //if no potential moves, CleanSweep is stuck
        if (potentialMoves.size() == 0) {
            System.out.println("\nCleanSweep is stuck, cannot move");
            return false;
        }

        //
        Point nextMove = pickPointToMoveTo(potentialMoves);

        FloorTileType currentFloorTileType = getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType = floorMap.getFloorTileAt(nextMove).getType();

        //if not enough battery left, move operation fails, battery dead
        if (reduceChargeOnMove(currentFloorTileType, nextFloorTileType)) {
            if (cleanSweepCommands.move(nextMove)) {
                floorMap.getFloorTileAt(nextMove).setVisited();
                removePointFromUnvisitedList(nextMove);


                return true;
            }
        }
        return false;
    }


    public void startCleaningCycle() {
        // point -> (0,1)

        /*
            1. Check battery level, if not full, charge to full battery
            2. While dirt capacity is not full and battery is not dead
                -> try to move to another tile
                -> try to clean that tile
         */

        //check battery level at start of cycle
        tryToChargeBattery();

        while (checkBattery() && (getCurrentVacuumDirt() < MAX_DIRT_CAPACITY)) {

            if (tryToMove()) {
                tryToClean();
            } else {
                break;
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

    public boolean tryToMoveUp() {
        if (!checkBattery()) {
            return false;
        }

        if (isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY() + 1)) {
            return false;
        }
        FloorTileType currentFloorTileType = getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType = floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY() + 1).getType();

        //if not enough battery left, operation fails, battery dead
        if (!reduceChargeOnMove(currentFloorTileType, nextFloorTileType)) {
            return false;
        }

        System.out.println("   Moving up");
        cleanSweepCommands.moveUp();

        return true;
    }

    public boolean tryToMoveDown() {
        if (!checkBattery()) {
            return false;
        }

        if (isWallOrObstacle(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY() - 1)) {
            return false;
        }
        FloorTileType currentFloorTileType = getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType = floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY() - 1).getType();

        //if not enough battery left, operation fails, battery dead
        if (!reduceChargeOnMove(currentFloorTileType, nextFloorTileType)) {
            return false;
        }

        System.out.println("   Moving down");
        cleanSweepCommands.moveDown();

        return true;
    }

    public boolean tryToMoveLeft() {
        if (!checkBattery()) {
            return false;
        }

        if (isWallOrObstacle(cleanSweepCommands.getCurrentX() - 1, cleanSweepCommands.getCurrentY())) {
            return false;
        }
        FloorTileType currentFloorTileType = getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType = floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX() - 1, cleanSweepCommands.getCurrentY()).getType();

        //if not enough battery left, operation fails, battery dead
        if (!reduceChargeOnMove(currentFloorTileType, nextFloorTileType)) {
            return false;
        }

        System.out.println("   Moving left");
        cleanSweepCommands.moveLeft();

        return true;
    }

    public boolean tryToMoveRight() {
        if (!checkBattery()) {
            return false;
        }

        if (isWallOrObstacle(cleanSweepCommands.getCurrentX() + 1, cleanSweepCommands.getCurrentY())) {
            return false;
        }
        FloorTileType currentFloorTileType = getCurrentFloorTile().getType();
        FloorTileType nextFloorTileType = floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX() + 1, cleanSweepCommands.getCurrentY()).getType();

        if (!reduceChargeOnMove(currentFloorTileType, nextFloorTileType)) {
            return false;
        }

        System.out.println("   Moving right");
        cleanSweepCommands.moveRight();

        return true;
    }

    private boolean isWallOrObstacle(int x, int y) {
        return (null == floorMap.getFloorTileAt(x, y) || floorMap.getFloorTileAt(x, y).isObstacle());

    }

    private boolean isWallOrObstacle(Point p) {
        return (null == floorMap.getFloorTileAt(p) || floorMap.getFloorTileAt(p).isObstacle());
    }

    private boolean isVisited(Point p) {
        return (null != floorMap.getFloorTileAt(p) && floorMap.getFloorTileAt(p).isVisited());
    }

    private boolean reduceChargeOnClean(FloorTileType floorTileType) {
        unitsOfCharge -= floorTileType.getValue();
        System.out.println("   Reduced charge from cleaning " + floorTileType + ": -" + floorTileType.getValue());
        System.out.println("   Current battery level: " + getCurrentBatteryLevel());
        if (getCurrentBatteryLevel() > 0) {
            return true;
        } else {
            System.out.println("\nBattery dead");
            return false;
        }
    }

    private boolean reduceChargeOnMove(FloorTileType currentTile, FloorTileType nextTile) {
        double batteryCostToMove = (double) (currentTile.getValue() + nextTile.getValue()) / 2;
        unitsOfCharge -= batteryCostToMove;
        System.out.println("   Reduced charge from moving from " + currentTile + " to " + nextTile + " -" + batteryCostToMove);
        System.out.println("   Current battery level: " + getCurrentBatteryLevel());
        if (getCurrentBatteryLevel() > 0) {
            return true;
        } else {
            System.out.println("\nBattery dead");
            return false;
        }
    }

    public void tryToChargeBattery() {
        if (getCurrentFloorTile().isChargingStation()) {
            unitsOfCharge = MAX_BATTERY_CAPACITY;
        }
        System.out.println("   Charged battery to: " + getCurrentBatteryLevel());
    }

    public boolean checkBattery() {
        return (unitsOfCharge > 0);
    }

    public double getCurrentBatteryLevel() {
        return unitsOfCharge;
    }

    public int getCurrentVacuumDirt() {
        return currentVacuumDirt;
    }

    public FloorTile getCurrentFloorTile() {
        return floorMap.getFloorTileAt(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY());
    }

    private boolean tryToClean() {
        if (!checkBattery()) {
            return false;
        }

        FloorTile floorTile = getCurrentFloorTile();

        // if current dirt is less than max dirt and the tile is dirty, remove dirt
        while (currentVacuumDirt < MAX_DIRT_CAPACITY && floorTile.isDirty()) {
            floorTile.removeDirt();
            currentVacuumDirt += 1;
            System.out.println("   Tile dirty, dirt added.");
            System.out.println("   Current vacuum dirt level: " + currentVacuumDirt);

            //if we are able to clean, battery charge will be reduced based on flooring type
            //if not enough battery left, operation fails, battery dead
            if (!reduceChargeOnClean(floorTile.getType())) {
                return false;
            }
        }

        // if current dirt is less than max dirt and the tile is not dirty, do not remove dirt
        if (currentVacuumDirt < MAX_DIRT_CAPACITY && !floorTile.isDirty()) {
            System.out.println("   Tile not dirty, no dirt added.");
            System.out.println("   Current vacuum dirt level: " + currentVacuumDirt);
            return false;
        }
        // if current dirt is more than max dirt do not remove dirt
        else if (currentVacuumDirt >= MAX_DIRT_CAPACITY) {
            System.out.println("   Current vacuum dirt level: " + currentVacuumDirt);
            System.out.println("   Vacuum full. No longer cleaning.");
            return false;
        }

        return true;
    }




}
