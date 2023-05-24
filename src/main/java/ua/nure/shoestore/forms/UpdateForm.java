package ua.nure.shoestore.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateForm {
    private long id;
    private String name;
    private String password;
    private String surname;
    private String phoneNumber;
    private String email;
}
