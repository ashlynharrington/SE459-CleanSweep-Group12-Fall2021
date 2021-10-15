package test.floor;

import main.tiles.*;

public class ExampleFloorTiles {

    public static void main(String[] args) {

        /*
            example layout is of a 3x3 square room, 9 main.floor tiles
            a (0,0)     b (0,1)      c (0,2)
            d-4 (1,0)   e-(1,1)      f-6 (1,2)
            g-7 (2,0)   h-8 (2,1)    i-9 (2,2)
         */

        //cell "f" is also a charging station

        FloorTile a = new FloorTile(0, 2, FloorTileType.LOWCARPET, 2);
        FloorTile b = new FloorTile(1, 2, FloorTileType.LOWCARPET, 1);
        FloorTile c = new FloorTile(2, 2, FloorTileType.BARE, 0);

        FloorTile d = new FloorTile(0, 1, FloorTileType.LOWCARPET, 0);
        FloorTile e = new FloorTile(1, 1, FloorTileType.LOWCARPET, 1);
        FloorTile f = new FloorTile(2, 1, FloorTileType.BARE, 0, true);


        FloorTile g = new FloorTile(0, 0, FloorTileType.HIGHCARPET, 0);
        FloorTile h = new FloorTile(1, 0, FloorTileType.HIGHCARPET, 1);
        FloorTile i = new FloorTile(2, 0, FloorTileType.HIGHCARPET, 1);





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


        /*
        System.out.println("Getting FloorTiles at Coordinates:");

        System.out.println(floorTileSet.getFloorTileAt(1, 1));

        System.out.println(floorTileSet.getFloorTileAt(2, 1));


        System.out.println("Printing entire FloorMap:");
        System.out.println(floorTileSet);

         */

        System.out.println("The hashmap is " + floorTileSet.getFloorTileAt(1,1));


    }

}
