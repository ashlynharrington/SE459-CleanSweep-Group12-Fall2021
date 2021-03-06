package SE459.CleanSweep.cleansweep;
import SE459.CleanSweep.tiles.FloorPoint;


/**
 * Interface meant to make sure that the way the Clean Sweep maintains state can change without
 * automatically breaking anything else.
*/

//Also meant to document all the current ways that the Clean Sweep state can change.

public interface CleanSweepStateInterface {
    boolean move(FloorPoint floorPoint);
    void moveUp();
    void moveDown();
    void moveRight();
    void moveLeft();
    int getCurrentX();
    int getCurrentY();
}
