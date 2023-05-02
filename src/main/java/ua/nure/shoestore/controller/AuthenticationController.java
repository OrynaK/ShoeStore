package ua.nure.shoestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.forms.LoginForm;
import ua.nure.shoestore.service.AddressService;
import ua.nure.shoestore.service.AuthenticationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping(value={"/registration"})
    public String registration(@RequestBody User user){
        if(user != null){
            service.addUser(user);
            return "New user added";
        } else return "";
    }


    @PostMapping(value = "/login")
    public User loginUser(@RequestBody LoginForm loginForm){
        User user = service.logIn(loginForm.getEmail(), loginForm.getPassword());
        return Objects.requireNonNullElseGet(user, User::new);
    }


    @GetMapping(value = "/getAll")
    public List<User> getAll(){
        return new ArrayList<>();
    }


}
