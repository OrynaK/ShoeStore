package ua.nure.shoestore.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoeOrder {
    private Long shoeId;
    private BigDecimal price;
    private int amount;
    private String name;
    private BigDecimal size;
    private String color;

    public ShoeOrder(Long shoeId, BigDecimal price, int amount){
        this.shoeId=shoeId;
        this.price=price;
        this.amount=amount;
    }

}
