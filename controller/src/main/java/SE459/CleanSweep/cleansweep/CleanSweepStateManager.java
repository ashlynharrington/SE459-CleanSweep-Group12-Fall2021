package SE459.CleanSweep.cleansweep;

import SE459.CleanSweep.tiles.FloorPoint;

public class CleanSweepStateManager implements CleanSweepStateInterface {
    private int currentX;
    private int currentY;
    private int lastX;
    private int lastY;

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

    public boolean move(FloorPoint p){
        int dx = Math.abs(Math.abs(currentX) - Math.abs(p.getX()));
        int dy = Math.abs(Math.abs(currentY) - Math.abs(p.getY()));

        if(dx == 1 && dy == 0){
            lastX = currentX;
            currentX = p.getX();
            logState();
            return true;
        } else if (dx == 0 && dy == 1){
            lastY = currentY;
            currentY = p.getY();
            logState();
            return true;
        } else {
            return false;
        }

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
