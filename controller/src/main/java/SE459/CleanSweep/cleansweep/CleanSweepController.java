package SE459.CleanSweep.cleansweep;

import SE459.CleanSweep.pathfinding.Path;
import SE459.CleanSweep.pathfinding.PathFinder;
import SE459.CleanSweep.tiles.FloorTile;
import SE459.CleanSweep.tiles.FloorTileSet;
import SE459.CleanSweep.tiles.FloorTileType;
import SE459.CleanSweep.tiles.FloorPoint;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CleanSweepController {
    Path heuristic = null;
    private static final int MAX_BATTERY_CAPACITY = 250;
    private static final int MAX_DIRT_CAPACITY = 50;
    private static final double LOW_BATTERY_THRESHOLD = 30;

    private CleanSweepStateInterface cleanSweepCommands;
    FloorTileSet floorMap;

    //list to hold coordinates when CleanSweep observes tiles around it
    //coordinates of unvisited tiles are saved at the point the CleanSweep "sees" them
    private final Set<FloorPoint> sensedUnvisitedPoints;
    private final Set<FloorPoint> visitedPoints;

    private int currentVacuumDirt = 0;
    private int totalDirtCollected = 0;

    private double unitsOfCharge = 20.0;
    private ByteArrayOutputStream baos;

    public CleanSweepController(FloorTileSet floorPlan) {
        try {
            baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cleanSweepCommands = new CleanSweepStateManager(1, 1);
        floorMap = floorPlan;
        sensedUnvisitedPoints = new HashSet<>();
        visitedPoints = new HashSet<>();
    }


    private ArrayList<FloorPoint> senseAdjacentTiles() {
        ArrayList<FloorPoint> potentialMoves = new ArrayList<>();

        int currentY = cleanSweepCommands.getCurrentY();
        int currentX = cleanSweepCommands.getCurrentX();

        FloorPoint up = new FloorPoint(currentX, currentY + 1);
        FloorPoint down = new FloorPoint(currentX, currentY - 1);
        FloorPoint right = new FloorPoint(currentX + 1, currentY);
        FloorPoint left = new FloorPoint(currentX - 1, currentY);

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

    private FloorPoint pickPointToMoveTo(ArrayList<FloorPoint> potentialMoves) {
        FloorPoint move = null;

        if(isLowBattery()){

            FloorPoint currentFloorPoint = new FloorPoint(cleanSweepCommands.getCurrentX(),cleanSweepCommands.getCurrentY());
            FloorPoint chargingStation = getChargingStation();

            if(currentFloorPoint.equals(chargingStation)){
                //Already on charging station
                tryToChargeBattery();

            }else {

                System.out.println("Returning to charging station");

                if (null == chargingStation) {
                    //Should not happen if there is a charging station
                    System.out.println("Could not find a charging station.  Run until battery dead.");
                } else {
                    PathFinder chargePathFinder = new PathFinder(
                            new FloorPoint(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()),
                            chargingStation,
                            floorMap
                    );
                    Path chargePath = chargePathFinder.findPath();
                    chargePath.printPath();
                    return chargePath.getNextMove();
                }
            }
        }


        //if only one move is available, move there
        if (potentialMoves.size() == 1) {
            move = potentialMoves.get(0);
            return move;
        }

        //try to move to an unvisited tile first
        for (FloorPoint p : potentialMoves) {
            if (!floorMap.getFloorTileAt(p).isVisited()) {
                move = new FloorPoint(p.getX(), p.getY());
                break;
            }
        }

        System.out.println("   Sensed Unvisited Tiles List: " + sensedUnvisitedPoints.toString());
        //if no unvisited tiles to move to, try to go toward an unvisited tile

        if (move == null) {
            FloorPoint sensedButUnvisitedPoint = null;
            Object[] arrayOfUnvisited = sensedUnvisitedPoints.toArray();
            if(arrayOfUnvisited.length == 0){
                System.out.println("\nVisited all visible tiles!!\n");
                move = new FloorPoint(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY());
            }else {
               sensedButUnvisitedPoint  = (FloorPoint)arrayOfUnvisited[0];
            }
            PathFinder pf = new PathFinder(
                    new FloorPoint(cleanSweepCommands.getCurrentX(), cleanSweepCommands.getCurrentY()),
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

    private void removePointFromUnvisitedList(FloorPoint p) {
        for (FloorPoint floorPoint : sensedUnvisitedPoints) {
            if (floorPoint.equals(p)) {
                sensedUnvisitedPoints.remove(floorPoint);
                visitedPoints.add(floorPoint);
                System.out.println("   Removed Point from unvisited list: " + floorPoint.toString());
                System.out.println("   Added Point to visited list: " + floorPoint.toString());
                System.out.println("   Tiles visited: " + visitedPoints);
                break;
            }
        }
    }


    private boolean tryToMove() {
        //first the CleanSweep looks around at adjacent tiles for potential moves
        ArrayList<FloorPoint> potentialMoves = senseAdjacentTiles();

        //if no potential moves, CleanSweep is stuck
        if (potentialMoves.size() == 0) {
            System.out.println("\nCleanSweep is stuck, cannot move");
            return false;
        }

        FloorPoint nextMove = pickPointToMoveTo(potentialMoves);
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


    public void startCleaningCycle() throws InterruptedException {
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
    }

    //Return boolean stating if moving up one tile succeeded or failed due to obstacle or wall.

    private boolean isWallOrObstacle(FloorPoint p) {
        return (null == floorMap.getFloorTileAt(p) || floorMap.getFloorTileAt(p).isObstacle());
    }

    private boolean isVisited(FloorPoint p) {
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

    public int getTotalDirtCollected() {
        return totalDirtCollected;
    }

    public Set<FloorPoint> getVisitedPoints() {
        return visitedPoints;
    }

    public String getLogs(){
        return baos.toString();
    }

    @Override
    public String toString() {
        return "Current Battery Level = " + unitsOfCharge;
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
            totalDirtCollected += 1;

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


    private FloorPoint getChargingStation(){
        return randomChargingStation();
    }
    private FloorPoint randomChargingStation(){
        Set<FloorPoint> pointsToScan = floorMap.getFloorMap().keySet();
        for(FloorPoint p: pointsToScan){
            if(floorMap.getFloorTileAt(p).isChargingStation()){
                return p;
            }
        }
        return null;
    }

    private boolean isLowBattery(){
        return unitsOfCharge<=LOW_BATTERY_THRESHOLD;
    }



}
