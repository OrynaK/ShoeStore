package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ua.nure.shoestore.dto.UpdateRoleDTO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.service.ShoeService;
import ua.nure.shoestore.service.UserService;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private ShoeService shoeService;
    @Autowired
    private UserService userService;

    public AdminController(ShoeService service, UserService userService) {
        this.shoeService = service;
        this.userService=userService;
    }

    @PostMapping(value = {"/addNewShoe"})
    public String addShoe(@RequestBody Shoe shoe) {
        if (shoe != null) {
            shoeService.addShoe(shoe);
            return "New shoe added";
        } else return "";
    }

    @PostMapping(value = "/updateUserRole")
    public String updateRole(@RequestBody UpdateRoleDTO userInfo) {
        if (userInfo.getId() > 0) {
            userService.updateRole(userInfo.getId(), userInfo.getRole());
            return "User role updated";
        }
        return null;
    }

}
