package ua.nure.shoestore.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ua.nure.shoestore.dto.UpdateRoleDTO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Role;
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
    public String registration(@RequestBody Shoe shoe) {
        if (shoe != null) {
            shoeService.addShoe(shoe);
            return "New shoe added";
        } else return "";
    }

    @PostMapping(value = "/updateUserRole")
    public String updateStatus(@RequestBody UpdateRoleDTO userInfo) {
        if (userInfo.getUser_id() > 0) {
            userService.updateRole(userInfo.getUser_id(), userInfo.getRole());
            return "User role updated";
        }
        return null;
    }

}
