package SE459.CleanSweep.controller;

import SE459.CleanSweep.model.User;
import SE459.CleanSweep.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Register {

    /*
        Model: name, birth date, email
        View: html page
        Controller: handles links
     */

    @GetMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("welcome");
        return modelAndView;
    }


    @GetMapping (value="/register")
    public ModelAndView showForm (Model model) {
        model.addAttribute("user",new User());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("user") User user) {
        System.out.println(user);
        return "register_success";
    }

    /*
    @PostMapping(value="/addUser")
    public String addUser (Model model,@ModelAttribute("user") User user, BindingResult bindingResult) {
        userRepository.save(user);
        model.addAttribute("firstName",user.getFirstName());

        return "adding user";
    }
     */


}
