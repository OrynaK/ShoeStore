package ua.nure.shoestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.forms.LoginForm;
import ua.nure.shoestore.service.AuthenticationService;
import ua.nure.shoestore.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;
    private CartService cartService;

    public AuthenticationController(AuthenticationService service, CartService cartService) {
        this.service = service;
        this.cartService = cartService;
    }

    @PostMapping(value={"/registration"})
    public String registration(@RequestBody User user, Cart cart){
        if(user != null){
            service.addUser(user, cart);

            return "New user added and cart created";
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
