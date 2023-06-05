package ua.nure.shoestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BestUserDTO {
    private Long user_id;
    private String name;
    private String surname;
    private Long order_id;
    private BigDecimal total_spent;

    public BestUserDTO() {
    }

}

