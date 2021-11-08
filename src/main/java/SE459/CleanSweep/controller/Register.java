package SE459.CleanSweep.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Register {
    /*
    // view html page
    // Model name, brith date, email
    controller handles links
     */


    @RequestMapping(method = RequestMethod.GET,value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("welcome");
        return modelAndView;
    }


    @RequestMapping (value="/register")
    public ModelAndView registerUser () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping(value="/addUser")
    public String addUser () {
       return "adding user";
    }


}
