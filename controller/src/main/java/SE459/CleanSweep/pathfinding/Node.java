package SE459.CleanSweep.pathfinding;


import SE459.CleanSweep.tiles.FloorPoint;

public class Node implements Comparable<Node>{
    FloorPoint coordinates;
    Node parent;
    int estimateDistance = 0;

    public Node(FloorPoint c, Node p){
        coordinates = c;
        parent = p;
    }

    private int getHeuristicMeasure(FloorPoint other){
        return Math.abs(other.getX()-coordinates.getX())+ Math.abs(other.getY()- coordinates.getY());
    }

    public int getEstimatedDistanceTo(FloorPoint other){
        estimateDistance = getHeuristicMeasure(other)+getDistanceFromStart();
        return estimateDistance;
    }

    public int getDistanceFromStart(){
        if(parent == null){
            return 0;
        }else{
            return 1+parent.getDistanceFromStart();
        }
    }

    public FloorPoint getCoordinates() {
        return coordinates;
    }



    public Node reverse(){
        Node currentNode = this;
        Node reversed = null;
        if(null == currentNode.parent){
            return currentNode;
        }else{
           reversed = currentNode.parent.reverse();
           currentNode.parent.parent= currentNode;
           currentNode.parent = null;
        }



        return reversed;
    }


    @Override
    public int compareTo(Node o) {
        return this.estimateDistance - o.estimateDistance;
    }
}
