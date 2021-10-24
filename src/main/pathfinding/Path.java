package main.pathfinding;

import main.tiles.Point;

public class Path {
    Node startingPoint;
    Node iterator;

    public Path(Node exploredNode) {
        startingPoint = exploredNode.reverse();
        iterator = startingPoint;
    }
    public void printPath(){
        Node iterator = startingPoint;
        while(iterator!=null){
            System.out.println(iterator.getCoordinates().toString());
            iterator = iterator.parent;
        }
    }
    public Point getNextMove(){
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
