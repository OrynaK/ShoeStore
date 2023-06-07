package ua.nure.shoestore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDTO {
    private long id;
    
    @NotBlank(message = "Ім'я не може бути порожнім")
    @Size(max = 30, message = "Ім'я не може бути довше 30 символів")
    private String name;

    @NotBlank(message = "Пароль не може бути порожнім")
    @Size(max = 45, message = "Пароль не може бути довше 45 символів")
    private String password;

    @NotBlank(message = "Прізвище не може бути порожнім")
    @Size(max = 30, message = "Прізвище не може бути довше 30 символів")
    private String surname;

    @NotBlank(message = "Номер телефону не може бути порожнім")
    @Size(max = 13, message = "Номер телефону не може бути довше 13 символів")
    private String phoneNumber;

    @NotBlank(message = "Пошта не може бути порожньою")
    @Size(max = 30, message = "Пошта не може бути довше 30 символів")
    @Email(message = "Неправильний формат пошти")
    private String email;
}
