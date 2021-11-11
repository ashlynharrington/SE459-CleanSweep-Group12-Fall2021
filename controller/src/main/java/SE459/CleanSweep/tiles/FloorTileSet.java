package SE459.CleanSweep.tiles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FloorTileSet {

    private final Map<SimulatorPoint, FloorTile> floorCells;

    public FloorTileSet(){
        floorCells = new HashMap<>();
    }

    public FloorTile getFloorTileAt(int x, int y){
        return floorCells.get(new SimulatorPoint(x, y));
    }
    public FloorTile getFloorTileAt(SimulatorPoint p){
        return floorCells.get(p);
    }

    public void addFloorCell(FloorTile floorCell){
        floorCells.put(floorCell.getLocation(), floorCell);
    }

    public String toString(){
        Set<Map.Entry<SimulatorPoint, FloorTile>> set = floorCells.entrySet();
        Iterator<Map.Entry<SimulatorPoint, FloorTile>> iterator = set.iterator();
        StringBuilder sb = new StringBuilder();

        while(iterator.hasNext()) {
            Map.Entry<SimulatorPoint, FloorTile> mapEntry = iterator.next();
            sb.append("\n");
            sb.append(mapEntry.getValue());
        }
        return sb.toString();
    }

    public Map<SimulatorPoint, FloorTile> getFloorMap() {
        return floorCells;
    }

    public int getFloorMapSize() {
        return floorCells.size();
    }

}
