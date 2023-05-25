package ua.nure.shoestore.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ShoeOrder {
    private long shoeId;
    private BigDecimal price;
    private int amount;
}