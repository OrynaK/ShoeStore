package ua.nure.shoestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.LoginDTO;
import ua.nure.shoestore.entity.User;
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

    @PostMapping(value = {"/registration"})
    public User registration(@RequestBody User user) {
        if (user != null) {
            return service.addUser(user);
        } else return null;
    }

    @PostMapping(value = "/login")
    public User loginUser(@RequestBody LoginDTO loginDTO) {
        User user = service.logIn(loginDTO.getEmail(), loginDTO.getPassword());
        return Objects.requireNonNullElseGet(user, User::new);
    }


    @GetMapping(value = "/getAll")
    public List<User> getAll() {
        return new ArrayList<>();
    }

}
