package SE459.CleanSweep.pathfinding;

import SE459.CleanSweep.pathfinding.Path;
import SE459.CleanSweep.pathfinding.PathFinder;
import SE459.CleanSweep.tiles.FloorTile;
import SE459.CleanSweep.tiles.FloorTileSet;
import SE459.CleanSweep.tiles.FloorTileType;
import SE459.CleanSweep.tiles.FloorPoint;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.FileReader;
import java.io.IOException;

public class PathFinderTest {
    public static void main(String [] args){
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

            PathFinder pf = new PathFinder(new FloorPoint(0,0), new FloorPoint(3,0), floorMap);
            Path p = pf.findPath();
            p.printPath();




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
