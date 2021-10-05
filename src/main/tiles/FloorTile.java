package main.tiles;

public class FloorTile {

    private final FloorTileType type;
    private Boolean visited;
    private Boolean isChargingStation;
    private int unitsOfDirt;
    private final Point location;

    //Constructors

    public FloorTile(int x, int y, FloorTileType type, int unitsOfDirt){
        this.location = new Point(x, y);
        this.type = type;
        this.unitsOfDirt = unitsOfDirt;
        this.isChargingStation = false;
        this.visited = false;
    }

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
                "\n  isChargingStation: " + isChargingStation.toString() +
                "\n  isVisited: " + visited.toString() +
                "\n  unitsOfDirt: " + unitsOfDirt +
                "\n";
    }

}
