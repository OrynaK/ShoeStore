package ua.nure.shoestore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDTO {
    private long id;
    private String name;
    private String password;
    private String surname;
    private String phoneNumber;
    private String email;
}
