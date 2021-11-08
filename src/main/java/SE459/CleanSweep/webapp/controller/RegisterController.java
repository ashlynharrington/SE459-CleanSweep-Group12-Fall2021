package SE459.CleanSweep.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class register {

    @RequestMapping("/register")
    public String register() {
        return "Register";
    }

}
