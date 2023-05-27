package ua.nure.shoestore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartShoeDTO {
    private long userId;
    private long shoeId;
    private BigDecimal price;
    private int amount;
}
