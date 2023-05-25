package ua.nure.shoestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private long id;
    private long userId;
    private List<ShoeOrder> shoesInCart;
}
