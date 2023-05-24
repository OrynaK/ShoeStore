package ua.nure.shoestore.entity;

import lombok.Data;
import ua.nure.shoestore.entity.enums.Role;

@Data
public class User {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
}
