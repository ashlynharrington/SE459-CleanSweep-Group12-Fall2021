package main.cleansweep;

/**
 * Interface meant to make sure that the way the Clean Sweep maintains state can change without
 * automatically breaking anything else.
*/

//Also meant to document all the current ways that the Clean Sweep state can change.

public interface CleanSweepStateInterface {
    void moveUp();
    void moveDown();
    void moveRight();
    void moveLeft();
    int getCurrentX();
    int getCurrentY();
}
