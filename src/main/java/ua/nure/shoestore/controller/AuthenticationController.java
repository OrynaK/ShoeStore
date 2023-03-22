package ua.nure.shoestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.service.AuthenticationService;

@Controller
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @PostMapping(value={"/authentication"})
    public String addUser(@RequestBody User user){
        service.addUser(user);
        return "redirect:/main";
    }
}
