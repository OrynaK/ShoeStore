package ua.nure.shoestore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotNull(message = "Поле обов'язкове для заповнення")
    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Email(message = "Невірний формат електронної пошти")
    private String email;

    @NotNull(message = "Поле обов'язкове для заповнення")
    @NotBlank(message = "Поле обов'язкове для заповнення")
    private String password;
}
