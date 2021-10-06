//java openjdk version "17" 2021-09-14, 64-Bit Server VM (build 17+35-2724, mixed mode, sharing)
//Ashlyn Harrington 10-03-2021

package main.floor;

import java.util.concurrent.atomic.AtomicInteger;

public class FloorCell {

    //using atomicinteger to auto-increment id number for main.floor cell
    private static final AtomicInteger countID = new AtomicInteger(0);
    private int id;

    //variables to stoor type of floor cell, if cell has been visited
    //if cell is the charging station, and the units of dirt in each cell
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

    //sets pointer north of cell
    public FloorCell setNorth(FloorCell north){
        this.north = north;
        return this;
    }
    //sets pointer south of cell
    public FloorCell setSouth(FloorCell south){
        this.south = south;
        return this;
    }

    //sets pointer east of cell
    public FloorCell setEast(FloorCell east){
        this.east = east;
        return this;
    }

    //sets pointer west of cell
    public FloorCell setWest(FloorCell west){
        this.west = west;
        return this;
    }

    //sets cell to charging station
    public void setAsChargingStation(){
        isChargingStation = true;
    }

    //function to return if cell is dirty
    //if >0 units of dirt, dirty = true
    public boolean isDirty(){
        if(unitsOfDirt != 0){
            return true;
        }
        return false;
    }

    //function to return FloorCell id
    public String getID(){
        return String.valueOf(id);
    }

    //returns the cell north of current cell
    public FloorCell getNorth(){
        return north;
    }

    //returns the cell south of current cell
    public FloorCell getSouth(){
        return south;
    }

    //returns the cell east of current cell
    public FloorCell getEast(){
        return east;
    }

    //returns the cell west of current cell
    public FloorCell getWest(){
        return west;
    }

    //returns dirt in a given cell
    public int getUnitsOfDirt(){
        return unitsOfDirt;
    }

    //returns the floor type of the cell
    public FloorCellType getFloorCellType(){
        return floorCellType;
    }

    //returns cell variables in string format
    //cell id, units of dirt, floor type, and whether it's a charging station
    public String toString() {
        return "FloorCell id: " + getID() +
                "\n  unitsOfDirt: " + getUnitsOfDirt() +
                "\n  floorCellType: " + getFloorCellType() +
                "\n  isChargingStation: " + isChargingStation.toString();
    }

}
