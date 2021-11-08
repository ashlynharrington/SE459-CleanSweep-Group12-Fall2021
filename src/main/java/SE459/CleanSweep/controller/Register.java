package SE459.CleanSweep.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Register {
    /*
    // view html page
    // Model name, brith date, email
    controller handles links
     */

    @RequestMapping("/register")
    public String register() {
        return "Register";
    }
}
