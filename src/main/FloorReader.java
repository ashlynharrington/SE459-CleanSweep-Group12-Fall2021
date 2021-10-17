package main;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import main.cleansweep.CleanSweepController;
import main.tiles.FloorTile;
import main.tiles.FloorTileSet;
import main.tiles.FloorTileType;

import java.io.FileReader;
import java.io.IOException;

public class FloorReader {

    public static void main(String[] args) {


        FloorTileSet floorMap = new FloorTileSet();


        try(FileReader reader = new FileReader("FloorPlan.json")) {
            System.out.println("File was found");
            Object obj = JsonParser.parseReader(reader);

            JsonArray floorPlanArray = (JsonArray) obj;

            System.out.println(floorPlanArray);

            JsonObject floorObject = (JsonObject) floorPlanArray.get(1);
            System.out.println(floorObject);

            for (JsonElement element:floorPlanArray) {
                System.out.println(element.getAsJsonObject().get("floor"));

                JsonObject floor = (JsonObject) element.getAsJsonObject().get("floor");

                int xCoordinate = floor.get("x").getAsInt();

                int yCoordinate = floor.get("y").getAsInt();

                String floorType = floor.get("floorType").getAsString();

                int unitsOfDirt = floor.get("unitsOfDirt").getAsInt();

                if(floor.get("isChargingStation") != null) {
                    FloorTile floorTileWithStation = new FloorTile(xCoordinate,yCoordinate, FloorTileType.valueOf(floorType), unitsOfDirt,true);
                    floorMap.addFloorCell(floorTileWithStation);
                } else if (floor.get("obstacle") != null) {
                    FloorTile floorTileWithObstacle =  new FloorTile(xCoordinate,yCoordinate,FloorTileType.valueOf(floorType),true,unitsOfDirt);
                    floorMap.addFloorCell(floorTileWithObstacle);
                } else {

                    FloorTile floorTile = new FloorTile(xCoordinate, yCoordinate, FloorTileType.valueOf(floorType), unitsOfDirt);
                    floorMap.addFloorCell(floorTile);
                }

            }

            CleanSweepController cleanSweepController = new CleanSweepController(floorMap);
            cleanSweepController.startCleaning();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
