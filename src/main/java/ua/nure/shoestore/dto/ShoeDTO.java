package ua.nure.shoestore.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.shoestore.entity.enums.Season;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoeDTO {
    private long id;
    @NotBlank(message = "Назва не може бути порожньою")
    private String name;
    @DecimalMin(value = "36.0", message = "Розмір повинен бути не менше 36.0")
    @DecimalMax(value = "43.0", message = "Розмір повинен бути не більше 43.0")
    @Digits(integer = 2, fraction = 1, message = "Розмір повинен мати формат xx.x")
    private BigDecimal size;
    @NotBlank(message = "Колір не може бути порожнім")
    private String color;
    @NotBlank(message = "Сезон не може бути порожнім")
    private Season season;
    @NotBlank(message = "Стать не може бути порожнім")
    private Sex sex;
    @DecimalMin(value = "0.01", message = "Ціна повинна бути більше або дорівнювати 0.01")
    private BigDecimal price;
    @Min(value = 0, message = "Кількість повинна бути більше або дорівнювати 0")
    private int amount;
    private String imagePath;

    private String imageName;

    public ShoeDTO(String name, BigDecimal size, String color, Season season, Sex sex, BigDecimal price, int amount) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.season = season;
        this.sex = sex;
        this.price = price;
        this.amount = amount;
    }

    public ShoeDTO(long id, String name, BigDecimal size, String color, Season season, Sex sex, BigDecimal price, int amount) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.color = color;
        this.season = season;
        this.sex = sex;
        this.price = price;
        this.amount = amount;
    }
}
