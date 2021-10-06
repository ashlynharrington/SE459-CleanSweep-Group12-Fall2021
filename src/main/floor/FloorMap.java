//java openjdk version "17" 2021-09-14, 64-Bit Server VM (build 17+35-2724, mixed mode, sharing)
//Ashlyn Harrington 10-03-2021


package main.floor;

public class FloorMap {

    //variables to track where cleansweep starts and currently is
    private FloorCell initialCleanSweepLocation;
    private FloorCell currentCleanSweepLocation;

    //instantiates floor map with the floor cells
    //sets initial and current location to the starting CleanSweep FloorCell
    public FloorMap(FloorCell initialCleanSweepLocation){
        this.initialCleanSweepLocation = initialCleanSweepLocation;
        this.currentCleanSweepLocation = initialCleanSweepLocation;
    }

    //sets current location to the current CleanSweep FloorCell
    public void setCurrentCleanSweepLocation(FloorCell currentLocation){
        this.currentCleanSweepLocation = currentLocation;
    }

    //function call to return starting CleanSweep FloorCell
    public FloorCell getInitialCleanSweepLocation(){
        return initialCleanSweepLocation;
    }

    //function call to return current CleanSweep FloorCell
    public FloorCell getCurrentCleanSweepLocation(){
        return currentCleanSweepLocation;
    }

}

