package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.forms.UpdateForm;
import ua.nure.shoestore.service.AddressService;
import ua.nure.shoestore.service.UserService;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/updateUserInfo")
    public User updateInfo(@RequestBody UpdateForm updateForm){
        if( updateForm!=null){
            return userService.updateInfo(updateForm);
        }
        return null;
    }
    @PostMapping(value="/updateUserRole")
    public String updateStatus(@RequestBody long user_id, Role role){
        if(user_id>0 && role!=null){
            userService.updateRole(user_id, role);
            return "User role updated";
        }
        return null;
    }

}
