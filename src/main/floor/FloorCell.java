//java openjdk version "17" 2021-09-14, 64-Bit Server VM (build 17+35-2724, mixed mode, sharing)
//Ashlyn Harrington 10-03-2021

package main.floor;

import java.util.concurrent.atomic.AtomicInteger;

public class FloorCell {

    //using atomicinteger to auto-increment id number for main.floor cell
    private static final AtomicInteger countID = new AtomicInteger(0);
    private int id;

    private FloorCellType floorCellType;
    private Boolean visited;
    private Boolean isChargingStation;
    private int unitsOfDirt;

    //pointers to adjacent FloorCells
    //default to null for stairs, wall, closed door, furniture, etc.
    private FloorCell north = null;
    private FloorCell south = null;
    private FloorCell east = null;
    private FloorCell west = null;

    //constructor providing main.floor type and units of dirt
    public FloorCell(FloorCellType type, int unitsOfDirt){
        this.floorCellType = type;
        this.unitsOfDirt = unitsOfDirt;
        this.isChargingStation = false;
        this.id = countID.incrementAndGet();
    }

    public FloorCell setNorth(FloorCell north){
        this.north = north;
        return this;
    }

    public FloorCell setSouth(FloorCell south){
        this.south = south;
        return this;
    }

    public FloorCell setEast(FloorCell east){
        this.east = east;
        return this;
    }

    public FloorCell setWest(FloorCell west){
        this.west = west;
        return this;
    }

    public void setAsChargingStation(){
        isChargingStation = true;
    }

    public boolean isDirty(){
        if(unitsOfDirt != 0){
            return true;
        }
        return false;
    }

    public String getID(){
        return String.valueOf(id);
    }

    public FloorCell getNorth(){
        return north;
    }
    public FloorCell getSouth(){
        return south;
    }
    public FloorCell getEast(){
        return east;
    }
    public FloorCell getWest(){
        return west;
    }

    public int getUnitsOfDirt(){
        return unitsOfDirt;
    }

    public FloorCellType getFloorCellType(){
        return floorCellType;
    }

    public String toString() {
        return "FloorCell id: " + getID() +
                "\n  unitsOfDirt: " + getUnitsOfDirt() +
                "\n  floorCellType: " + getFloorCellType() +
                "\n  isChargingStation: " + isChargingStation.toString();
    }

}
