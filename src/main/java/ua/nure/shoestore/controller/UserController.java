package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.dto.UpdateDTO;
import ua.nure.shoestore.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = "/getUsers")
    public List<User> getAll(){
        return userService.getUsers();
    }
    @GetMapping(value="/updateUserInfo")
    public User updateInfo(@RequestBody UpdateDTO updateDTO){
        if( updateDTO !=null){
            return userService.updateInfo(updateDTO);
        }
        return null;
    }


}
