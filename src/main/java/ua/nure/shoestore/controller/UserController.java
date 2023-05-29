package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.LoginDTO;
import ua.nure.shoestore.dto.UpdateDTO;
import ua.nure.shoestore.dto.UpdateRoleDTO;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.service.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = {"/registration"})
    public User registration(@RequestBody User user) {
        if (user != null) {
            return userService.addUser(user);
        } else return null;
    }

    @PostMapping(value = "/login")
    public User loginUser(@RequestBody LoginDTO loginDTO) {
        User user = userService.logIn(loginDTO.getEmail(), loginDTO.getPassword());
        return Objects.requireNonNullElseGet(user, User::new);
    }

    @GetMapping(value = "/getUsers")
    public List<User> getAll() {
        return userService.getUsers();
    }

    @PostMapping(value = "/updateUserInfo")
    public User updateInfo(@RequestBody UpdateDTO updateDTO) {
        if (updateDTO != null) {
            return userService.updateInfo(updateDTO);
        }
        return null;
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
