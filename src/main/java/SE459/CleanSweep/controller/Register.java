package SE459.CleanSweep.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Register {
    /*
    // view html page
    // Model name, brith date, email
    controller handles links
     */

    /*
    @RequestMapping("/register")
    public String register() {
        return "Register";
    }
     */

    @RequestMapping ("/register")
    public ModelAndView register (Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        model.addAttribute("greeting","this is a custom message");
        return modelAndView;
    }
}
