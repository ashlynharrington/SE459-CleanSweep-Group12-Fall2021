package SE459.CleanSweep.webapp.controller;

import SE459.CleanSweep.webapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "welcome";
    }

}