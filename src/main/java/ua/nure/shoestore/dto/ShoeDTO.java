package ua.nure.shoestore.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.shoestore.entity.enums.Season;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoeDTO {
    private long id;
    private String name;
    private BigDecimal size;
    private String color;
    private Season season;
    private Sex sex;
    private BigDecimal price;
    private int amount;
    private String imagePath;

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
