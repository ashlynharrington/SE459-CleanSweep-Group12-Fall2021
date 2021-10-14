package main.cleansweep;
import main.tiles.*;

public class CleanSweepStateManager implements CleanSweepStateInterface {
    private int currentX;
    private int currentY;


    public CleanSweepStateManager(int x, int y){
        currentX = x;
        currentY = y;
        logState();
    }


    private void decrementY(){
        currentY-=1;
    }

    private void incrementY(){
        currentY+=1;
    }

    private void incrementX(){
        currentX+=1;
    }

    private void decrementX(){
        currentX-=1;
    }

    public void moveUp(){
        decrementY();
        logState();
    }
    public void moveDown(){
        incrementY();
        logState();
    }
    public void moveRight(){
        incrementX();
        logState();
    }
    public void moveLeft(){
        decrementX();
        logState();
    }

    @Override
    public int getCurrentX() {
        return currentX;
    }

    @Override
    public int getCurrentY() {
        return currentY;
    }

    private void logState(){
        System.out.printf("Clean Sweep is currently on tile: (%d, %d)%n", currentX, currentY);
    }


}