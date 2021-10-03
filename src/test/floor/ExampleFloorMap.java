//java openjdk version "17" 2021-09-14, 64-Bit Server VM (build 17+35-2724, mixed mode, sharing)
//Ashlyn Harrington 10-03-2021

package test.floor;

import main.floor.*;

public class ExampleFloorMap {

    public static void main(String[] args) {

        //example layout is of a 3x3 square room, 9 main.floor tiles
        //  a-1  b-2  c-3
        //  d-4  e-5  f-6
        //  g-7  h-8  i-9

        //instantiating 9 main.floor cells with their main.floor type and units of dirt
        FloorCell a = new FloorCell(FloorCellType.LOWCARPET, 2);
        FloorCell b = new FloorCell(FloorCellType.LOWCARPET, 1);
        FloorCell c = new FloorCell(FloorCellType.BARE, 0);
        FloorCell d = new FloorCell(FloorCellType.LOWCARPET, 0);
        FloorCell e = new FloorCell(FloorCellType.LOWCARPET, 1);
        FloorCell f = new FloorCell(FloorCellType.BARE, 0);
        FloorCell g = new FloorCell(FloorCellType.HIGHCARPET, 0);
        FloorCell h = new FloorCell(FloorCellType.HIGHCARPET, 1);
        FloorCell i = new FloorCell(FloorCellType.HIGHCARPET, 1);

        //setting relative locations of each main.floor cell
        //each cell's 4 pointers are initiated to null, only need to set pointers for non-obstacle adjacent cells

        a.setEast(b).setSouth(d);
        b.setWest(a).setEast(c).setSouth(e);
        c.setWest(b).setSouth(f);
        d.setNorth(a).setEast(e).setSouth(g);
        e.setNorth(b).setSouth(h).setEast(f).setWest(d);
        f.setNorth(c).setWest(e).setSouth(i);
        g.setNorth(d).setEast(h);
        h.setNorth(e).setWest(g).setEast(i);
        i.setNorth(f).setWest(h);

        //assume cell "f" is the initial location of the Clean Sweep
        //cell "f" is also a charging station
        f.setAsChargingStation();

        //creating FloorMap by passing in where we set our Clean Sweep initially
        FloorMap floorMap = new FloorMap(f);

        //Pretendng we do not know where the CleanSweep starts, we can ask the FloorMap for the initial FloorCell
        FloorCell initialCell = floorMap.getInitialCleanSweepLocation();

        //printing out info for our initial cell
        System.out.println("Example starting point FloorCell:");
        System.out.println(initialCell);
        System.out.println();

        //we can look and check what is around the Clean Sweep
        System.out.println("Looking North: \n" + initialCell.getNorth());
        System.out.println();

        System.out.println("Looking South: \n" + initialCell.getSouth());
        System.out.println();

        System.out.println("Looking East: \n" + initialCell.getEast());
        System.out.println();

        System.out.println("Looking West: \n" + initialCell.getWest());
        System.out.println();

    }
}
