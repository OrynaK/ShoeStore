package ua.nure.shoestore.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Currency;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MakeOrderDTO {
    private Long userId;

    @NotBlank(message = "Поле обов'язкове для заповнення")
    private String country;

    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Size(max = 45, message = "Назва міста не може бути довше 45 символів")
    private String city;

    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Size(max = 45, message = "Вулиця не може бути довше 45 символів")
    private String street;

    @NotBlank(message = "Поле обов'язкове для заповнення")
    @Size(max = 10, message = "Номер будинку не може бути довше 10 символів")
    private String houseNumber;

    @Min(value = 1, message = "Номер під'їзду не може бути менше 1")
    private Integer entrance;

    @Min(value = 1, message = "Номер квартири не може бути менше 1")
    private Integer apartmentNumber;

    @NotEmpty(message = "Замовлення не може бути порожнім")
    private List<ShoeDTO> shoeOrder;


}
