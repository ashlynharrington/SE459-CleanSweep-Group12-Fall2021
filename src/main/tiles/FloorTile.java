package main.tiles;

public class FloorTile {
    /*

        //example layout is of a 3x3 square room, 9 main.floor tiles
         a (0,0)     b (0,1)      c (0,3)
         d-4 (1,0)   e-(1,1)      f-6 (1,2)
         g-7 (2,0)   h-8 (2,1)    i-9 (2,2)

     */
    //sets variables for floor type, if tile is visited
    //if tile is a charging station, the units of dirt on the tile, and the tile location

    private final FloorTileType type;
    private boolean visited;
    private boolean chargingStation;
    private int unitsOfDirt;
    private final Point location;
    private boolean obstacle;

    //constructors
    public FloorTile(int x, int y, FloorTileType type, int unitsOfDirt){
        this.location = new Point(x, y);
        this.type = type;
        this.unitsOfDirt = unitsOfDirt;
        this.chargingStation = false;
        this.visited = false;
        this.obstacle = false;
    }

    public FloorTile(int x, int y, FloorTileType type,boolean obstacle,int unitsOfDirt){
        this.location = new Point(x, y);
        this.type = type;
        this.unitsOfDirt = unitsOfDirt;
        this.chargingStation = false;
        this.visited = false;
        this.obstacle = obstacle;
    }

    //keep this second constructor?
    public FloorTile(int x, int y, FloorTileType type, int unitsOfDirt, boolean chargingStation){
        this.location = new Point(x, y);
        this.type = type;
        this.unitsOfDirt = unitsOfDirt;
        this.chargingStation = chargingStation;
        this.visited = false;
    }

    //getters
    public Point getLocation(){
        return location;
    }

    public FloorTileType getType(){
        return type;
    }

    //setters
    public void setAsChargingStation(){
        chargingStation = true;
    }

    public void setVisited(){
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public boolean isChargingStation(){ return chargingStation; }

    //dirt-related ideas
    public boolean isDirty() {
        return unitsOfDirt != 0;
    }

    // if there are any units of dirt, decrement units of dirt and return value
    public int removeDirt(){ if (unitsOfDirt > 0) {unitsOfDirt -= 1;} return unitsOfDirt; }

    public int getUnitsOfDirt() { return unitsOfDirt; };

    public String toString() {

        return "FloorTile at Location: " + getLocation() +
                "\n  Type: " + getType() +
                "\n  isChargingStation: " + chargingStation +
                "\n  isVisited: " + visited +
                "\n  obstacle: " + obstacle +
                "\n  unitsOfDirt: " + unitsOfDirt +
                "\n";
    }

}
