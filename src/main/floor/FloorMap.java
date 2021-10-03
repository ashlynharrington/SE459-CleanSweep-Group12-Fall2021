//java openjdk version "17" 2021-09-14, 64-Bit Server VM (build 17+35-2724, mixed mode, sharing)
//Ashlyn Harrington 10-03-2021


package main.floor;

public class FloorMap {

    private FloorCell initialCleanSweepLocation;
    private FloorCell currentCleanSweepLocation;

    public FloorMap(FloorCell initialCleanSweepLocation){
        this.initialCleanSweepLocation = initialCleanSweepLocation;
        this.currentCleanSweepLocation = initialCleanSweepLocation;
    }

    public void setCurrentCleanSweepLocation(FloorCell currentLocation){
        this.currentCleanSweepLocation = currentLocation;
    }

    public FloorCell getInitialCleanSweepLocation(){
        return initialCleanSweepLocation;
    }

    public FloorCell getCurrentCleanSweepLocation(){
        return currentCleanSweepLocation;
    }

}

