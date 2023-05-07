package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.cards.ShoeCard;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.forms.UpdateForm;
import ua.nure.shoestore.service.AddressService;
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
    public User updateInfo(@RequestBody UpdateForm updateForm){
        if( updateForm!=null){
            return userService.updateInfo(updateForm);
        }
        return null;
    }


}
