package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dto.LoginDTO;
import ua.nure.shoestore.dto.RegistrationDTO;
import ua.nure.shoestore.dto.UpdateDTO;
import ua.nure.shoestore.dto.UpdateRoleDTO;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.service.UserService;
import ua.nure.shoestore.utils.ErrorUtil;

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
    public ResponseEntity<Object> registration(@RequestBody @Validated RegistrationDTO userDTO, BindingResult bindingResult) throws DBException {
        ResponseEntity<Object> errors = ErrorUtil.getErrorsResponseEntity(bindingResult);
        if (errors != null) return errors;
        User user = userService.addUser(
                new User(userDTO.getName(), userDTO.getSurname(), userDTO.getEmail(), userDTO.getPhoneNumber(), userDTO.getPassword(), userDTO.getRole()));
        return ResponseEntity.ok(Objects.requireNonNullElseGet(user, User::new));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult) {
        ResponseEntity<Object> errors = ErrorUtil.getErrorsResponseEntity(bindingResult);
        if (errors != null) return errors;
        User user = userService.logIn(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(Objects.requireNonNullElseGet(user, User::new));
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
