package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dto.LoginDTO;
import ua.nure.shoestore.dto.UpdateDTO;
import ua.nure.shoestore.dto.UpdateRoleDTO;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public User registration(@RequestBody User user) throws DBException {
        if (user != null) {
            return userService.addUser(user);
        } else return null;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult) {
        ResponseEntity<Object> errors = getErrorsResponseEntity(bindingResult);
        if (errors != null) return errors;
        User user = userService.logIn(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(Objects.requireNonNullElseGet(user, User::new));
    }

    public static ResponseEntity<Object> getErrorsResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>(); // If there are validation errors, return a response with the field errors
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return null;
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
