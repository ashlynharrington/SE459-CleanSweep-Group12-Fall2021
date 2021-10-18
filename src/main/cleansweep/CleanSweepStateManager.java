package main.cleansweep;

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
        incrementY();
        logState();
    }
    public void moveDown(){
        decrementY();
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

        System.out.printf("\nClean Sweep is currently on tile: (%d, %d)%n", currentX, currentY);
        //System.out.printf("Clean Sweep has currently collected %d units of dirt%n", currentDirt);
    }

}
