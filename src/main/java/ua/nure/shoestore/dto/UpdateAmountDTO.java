package ua.nure.shoestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAmountDTO {
    private Long id;

    private int selectedAmount;
}
