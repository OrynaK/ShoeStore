package ua.nure.shoestore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoeCardDTO {
    private BigDecimal price;
    private String name;
    private String imageName;
}
