package ua.nure.shoestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.service.AuthenticationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping(value={"/authentication"})
    public String addUser(@RequestBody String name){
        System.out.println(name);
        //service.addUser(user);
        return "New student added";
    }

    @GetMapping(value = "/getAll")
    public List<User> getAll(){
        return new ArrayList<>();
    }
}
