package test.cleansweep;

import main.cleansweep.CleanSweepController;
import main.tiles.FloorTile;
import main.tiles.FloorTileSet;
import main.tiles.FloorTileType;

public class TestCleanSweepController {

    public static void testCleanSweep() {

        //example layout is of a 3x3 square room, 9 main.floor tiles
        //  a  b  c
        //  d  e  f
        //  g  h  i

        //cell "f" is also a charging station

        FloorTile g = new FloorTile(0, 0, FloorTileType.HIGHCARPET, 0);
        FloorTile h = new FloorTile(1, 0, FloorTileType.HIGHCARPET, 1);
        FloorTile i = new FloorTile(2, 0, FloorTileType.HIGHCARPET, 1);
        FloorTile d = new FloorTile(0, 1, FloorTileType.LOWCARPET, 0);
        FloorTile e = new FloorTile(1, 1, FloorTileType.LOWCARPET, 1);
        FloorTile f = new FloorTile(2, 1, FloorTileType.BARE, 0, true);
        FloorTile a = new FloorTile(0, 2, FloorTileType.LOWCARPET, 2);
        FloorTile b = new FloorTile(1, 2, FloorTileType.LOWCARPET, 1);
        FloorTile c = new FloorTile(2, 2, FloorTileType.BARE, 0);

        //creating FloorMap
        FloorTileSet floorTileSet = new FloorTileSet();

        //add example cells to floorTileSet
        floorTileSet.addFloorCell(a);
        floorTileSet.addFloorCell(b);
        floorTileSet.addFloorCell(c);
        floorTileSet.addFloorCell(d);
        floorTileSet.addFloorCell(e);
        floorTileSet.addFloorCell(f);
        floorTileSet.addFloorCell(g);
        floorTileSet.addFloorCell(h);
        floorTileSet.addFloorCell(i);

        CleanSweepController controller = new CleanSweepController(floorTileSet);
        controller.startCleaning();

    }
}
