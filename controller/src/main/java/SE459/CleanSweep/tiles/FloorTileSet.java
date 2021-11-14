package SE459.CleanSweep.tiles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FloorTileSet {

    private final Map<FloorPoint, FloorTile> floorCells;

    public FloorTileSet(){
        floorCells = new HashMap<>();
    }

    public FloorTile getFloorTileAt(int x, int y){
        return floorCells.get(new FloorPoint(x, y));
    }
    public FloorTile getFloorTileAt(FloorPoint p){
        return floorCells.get(p);
    }

    public void addFloorCell(FloorTile floorCell){
        floorCells.put(floorCell.getLocation(), floorCell);
    }

    public String toString(){
        Set<Map.Entry<FloorPoint, FloorTile>> set = floorCells.entrySet();
        Iterator<Map.Entry<FloorPoint, FloorTile>> iterator = set.iterator();
        StringBuilder sb = new StringBuilder();

        while(iterator.hasNext()) {
            Map.Entry<FloorPoint, FloorTile> mapEntry = iterator.next();
            sb.append("\n");
            sb.append(mapEntry.getValue());
        }
        return sb.toString();
    }

    public Map<FloorPoint, FloorTile> getFloorMap() {
        return floorCells;
    }

    public int getFloorMapSize() {
        return floorCells.size();
    }

}
