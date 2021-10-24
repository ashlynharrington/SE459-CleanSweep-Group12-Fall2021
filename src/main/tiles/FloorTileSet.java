package main.tiles;

import java.util.*;

public class FloorTileSet {

    private final Map<Point, FloorTile> floorCells;

    public FloorTileSet(){
        floorCells = new HashMap<>();
    }

    public FloorTile getFloorTileAt(int x, int y){
        return floorCells.get(new Point(x, y));
    }
    public FloorTile getFloorTileAt(Point p){
        return floorCells.get(p);
    }

    public void addFloorCell(FloorTile floorCell){
        floorCells.put(floorCell.getLocation(), floorCell);
    }

    public String toString(){
        Set<Map.Entry<Point, FloorTile>> set = floorCells.entrySet();
        Iterator<Map.Entry<Point, FloorTile>> iterator = set.iterator();
        StringBuilder sb = new StringBuilder();

        while(iterator.hasNext()) {
            Map.Entry<Point, FloorTile> mapEntry = iterator.next();
            sb.append("\n");
            sb.append(mapEntry.getValue());
        }
        return sb.toString();
    }

    public Map<Point, FloorTile> getFloorMap() {
        return floorCells;
    }

    public int getFloorMapSize() {
        return floorCells.size();
    }

}
