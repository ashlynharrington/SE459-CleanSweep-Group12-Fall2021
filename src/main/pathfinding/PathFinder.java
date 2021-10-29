package main.pathfinding;

import main.tiles.FloorTileSet;
import main.tiles.Point;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFinder {
    Point start;
    Point end;
    PriorityQueue<Node> nodes = new PriorityQueue<Node>();
    ArrayList<Node> finishedNodes = new ArrayList<Node>();
    ArrayList<Node> openNodes = new ArrayList<Node>();

    FloorTileSet floor;

    public PathFinder(Point s, Point e, FloorTileSet floorMap){
        start = s;
        end = e;
        floor = floorMap;
        Node startNode = new Node(start,null);
        nodes.add(startNode);
        openNodes.add(startNode);
    }

    public Path findPath(){
        if(null == end){
            return null;
        }
        while(!nodes.isEmpty()){
            Node exploredNode = explore();
            if(null!=exploredNode){
                return new Path(exploredNode);
            }
        }
        return null;
    }

    public Node explore(){
        Node bestNode =  nodes.poll();
        if(isEnd(bestNode)){

            return bestNode;
        }else {
            exploreLeft(bestNode);
            exploreRight(bestNode);
            exploreDown(bestNode);
            exploreUp(bestNode);
            openNodes.remove(bestNode);
            finishedNodes.add(bestNode);
            return null;
        }
    }

    private boolean isEnd(Node bestNode) {
        return (bestNode.getCoordinates().getX()==end.getX()) && (bestNode.getCoordinates().getY()==end.getY());
    }

    private void exploreLeft(Node bestNode) {
        Point p = bestNode.getCoordinates();
        int x = p.getX()-1;
        int y = p.getY();

        addNode(x,y,bestNode);
    }

    private void exploreRight(Node bestNode) {
        Point p = bestNode.getCoordinates();
        int x = p.getX()+1;
        int y = p.getY();

        addNode(x,y,bestNode);
    }

    private void exploreUp(Node bestNode) {
        Point p = bestNode.getCoordinates();
        int x = p.getX();
        int y = p.getY()+1;

        addNode(x,y,bestNode);
    }

    private void exploreDown(Node bestNode) {
        Point p = bestNode.getCoordinates();
        int x = p.getX();
        int y = p.getY()-1;

        addNode(x,y,bestNode);
    }

    private void addNode(int x, int y, Node bestNode){
        if(null != floor.getFloorTileAt(x,y) && (!floor.getFloorTileAt(x,y).isObstacle()) && (!finished(x,y))){
            Node neighborNode;
            neighborNode = new Node(new Point(x,y),bestNode);
            if(isOptimal(neighborNode)) {
                neighborNode.getEstimatedDistanceTo(end);
                nodes.add(neighborNode);
                openNodes.add(neighborNode);
            }

        }
    }

    private boolean isOptimal(Node neighborNode) {
        for(Node n: openNodes){
            if(n.getCoordinates().getX() == neighborNode.getCoordinates().getX() &&
                    n.getCoordinates().getY() == neighborNode.getCoordinates().getY() &&
                    n.getDistanceFromStart()<=neighborNode.getDistanceFromStart()
            ){
                return false;
            }
        }
        return true;
    }

    private boolean finished(int x, int y) {
        for(Node n: finishedNodes){
            Point p = n.getCoordinates();
            if(x==p.getX() && y==p.getY()){
                return true;
            }


        }
        return false;
    }


}
