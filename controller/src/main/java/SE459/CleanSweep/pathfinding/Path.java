package SE459.CleanSweep.pathfinding;


import SE459.CleanSweep.tiles.SimulatorPoint;

public class Path {
    Node startingPoint;
    Node iterator;

    public Path(Node exploredNode) {
        startingPoint = exploredNode.reverse().parent;
        iterator = startingPoint;
    }
    public void printPath(){
        Node iterator = startingPoint;
        while(iterator!=null){
            System.out.println(iterator.getCoordinates().toString());
            iterator = iterator.parent;
        }
    }
    public SimulatorPoint getNextMove(){
        Node nextMove;
        if(iterator == null){
            System.out.println("Already iterated through path.  Returning starting point");
            iterator = startingPoint;
            nextMove = iterator;
        }else {
             nextMove = iterator;
            iterator = iterator.parent;
        }
        return nextMove.getCoordinates();
    }
}
