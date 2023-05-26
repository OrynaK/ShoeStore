package ua.nure.shoestore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoeCardDTO {
    private BigDecimal price;
    private String name;
    private String imageName;

    public ShoeCardDTO(BigDecimal price, String name) {
        this.price = price;
        this.name = name;
        this.imageName = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoeCardDTO that = (ShoeCardDTO) o;
        return price.equals(that.price) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name);
    }
}
