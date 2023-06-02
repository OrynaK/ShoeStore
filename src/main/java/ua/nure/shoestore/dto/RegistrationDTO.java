package ua.nure.shoestore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ua.nure.shoestore.entity.enums.Role;

@Getter
@Setter
public class RegistrationDTO {

    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Size(max = 30, message = "Ім'я повинно містити до 30 символів")
    private String name;

    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Size(max = 30, message = "Прізвище повинно містити до 30 символів")
    private String surname;

    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Email(message = "Неправильний формат електронної пошти")
    @Size(max = 30, message = "Електронна пошта повинна містити до 30 символів")
    private String email;

    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Size(max = 13, message = "Номер телефону повинен містити до 13 символів")
    private String phoneNumber;

    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Size(max = 30, message = "Пароль повинен містити до 30 символів")
    private String password;

    public final Role role;

    public RegistrationDTO() {
        this.role = Role.CLIENT;
    }
}
