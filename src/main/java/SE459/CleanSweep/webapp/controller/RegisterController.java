package SE459.CleanSweep.webapp.controller;

import SE459.CleanSweep.cleansweep.CleanSweepController;
import SE459.CleanSweep.webapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import SE459.CleanSweep.tiles.*;
import SE459.CleanSweep.tiles.FloorTileSet;
import java.util.Map;
import static SE459.CleanSweep.FloorReader.readFloorMap;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String getRegistration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        System.out.println(user);

        try {

            FloorTileSet floorTileSet = readFloorMap();
            Map<Point, FloorTile> floorTileMap = floorTileSet.getFloorMap();


            CleanSweepController cleanSweepController = new CleanSweepController(floorTileSet);
            cleanSweepController.startCleaningCycle();
            cleanSweepController.getCurrentBatteryLevel();
            model.addAttribute("cleanSweepController", cleanSweepController);

            model.addAttribute("floorTileMap", floorTileMap);

        } catch (InterruptedException e) {
            System.err.println("Caught InterruptedException " + e);
        }
        return "welcome";
    }

}
