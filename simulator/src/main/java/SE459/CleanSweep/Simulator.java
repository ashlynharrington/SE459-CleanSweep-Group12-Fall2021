package SE459.CleanSweep;

import SE459.CleanSweep.cleansweep.CleanSweepController;
import SE459.CleanSweep.tiles.FloorTile;
import SE459.CleanSweep.tiles.FloorTileSet;
import SE459.CleanSweep.tiles.FloorTileType;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Simulator {

    public static void main(String[] args) {
        FloorTileSet floorMap = readFloorMap();

        try {

            CleanSweepController cleanSweepController = new CleanSweepController(floorMap);
            cleanSweepController.startCleaningCycle();

            System.out.println("\n\nCompleted Cleaning Cycle!!\n\n");
            System.out.println("Checking all Floor Tiles:");
            for (FloorTile floorTile : floorMap.getFloorMap().values()) {
                System.out.println(floorMap.getFloorTileAt(floorTile.getLocation().getX(), floorTile.getLocation().getY()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static FloorTileSet readFloorMap() {
        FloorTileSet floorMap = new FloorTileSet();

        try (FileReader reader = new FileReader("FloorPlan2.json")) {
            Object obj = JsonParser.parseReader(reader);

            JsonArray floorPlanArray = (JsonArray) obj;


            for (JsonElement element : floorPlanArray) {


                JsonObject floor = (JsonObject) element.getAsJsonObject().get("floor");

                int xCoordinate = floor.get("x").getAsInt();

                int yCoordinate = floor.get("y").getAsInt();

                String floorType = floor.get("floorType").getAsString();

                int unitsOfDirt = floor.get("unitsOfDirt").getAsInt();

                if (floor.get("isChargingStation") != null) {
                    FloorTile floorTileWithStation = new FloorTile(xCoordinate, yCoordinate, FloorTileType.valueOf(floorType), unitsOfDirt, true);
                    floorMap.addFloorCell(floorTileWithStation);
                } else if (floor.get("obstacle") != null) {
                    FloorTile floorTileWithObstacle = new FloorTile(xCoordinate, yCoordinate, FloorTileType.valueOf(floorType), true, unitsOfDirt);
                    floorMap.addFloorCell(floorTileWithObstacle);
                } else {

                    FloorTile floorTile = new FloorTile(xCoordinate, yCoordinate, FloorTileType.valueOf(floorType), unitsOfDirt);
                    floorMap.addFloorCell(floorTile);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException caught " + e);
        }
        return floorMap;
    }
}
