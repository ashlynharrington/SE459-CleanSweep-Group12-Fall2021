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
    private boolean isChargingStation;
    private int unitsOfDirt;
    private final Point location;

    //constructors
    public FloorTile(int x, int y, FloorTileType type, int unitsOfDirt){
        this.location = new Point(x, y);
        this.type = type;
        this.unitsOfDirt = unitsOfDirt;
        this.isChargingStation = false;
        this.visited = false;
    }

    //keep this second constructor?
    public FloorTile(int x, int y, FloorTileType type, int unitsOfDirt, boolean isChargingStation){
        this.location = new Point(x, y);
        this.type = type;
        this.unitsOfDirt = unitsOfDirt;
        this.isChargingStation = isChargingStation;
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
        isChargingStation = true;
    }

    public void setVisited(){
        visited = true;
    }

    //dirt-related ideas
    public boolean isDirty() {
        return unitsOfDirt != 0;
    }

    public void removeDirt(){
        //do something here to remove dirt?
    }

    public String toString() {

        return "FloorTile at Location: " + getLocation() +
                "\n  Type: " + getType() +
                "\n  isChargingStation: " + isChargingStation +
                "\n  isVisited: " + visited +
                "\n  unitsOfDirt: " + unitsOfDirt +
                "\n";
    }

}
