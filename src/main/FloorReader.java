package main;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import main.tiles.FloorTile;
import main.tiles.FloorTileSet;
import main.tiles.FloorTileType;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FloorReader {

    public static void main(String[] args) {


        FloorTileSet floorMap = new FloorTileSet();

        FloorTile [][] tiles = new FloorTile[3][3];

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

                FloorTile floorTile = new FloorTile(xCoordinate,yCoordinate, FloorTileType.valueOf(floorType), unitsOfDirt);

                tiles[xCoordinate][yCoordinate] = floorTile;

                floorMap.addFloorCell(floorTile);

            }

            System.out.println(floorMap.getFloorTileAt(0,0));
            System.out.println(Arrays.deepToString(tiles));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
