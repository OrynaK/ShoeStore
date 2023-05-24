package ua.nure.shoestore.entity;

import lombok.Data;
import ua.nure.shoestore.entity.enums.Season;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;

@Data
public class Shoe {
    private long id;
    private BigDecimal size;
    private String color;
    private Season season;
    private Sex sex;
    private BigDecimal price;
    private String name;
    private int amount;
    private int imageId;
}
