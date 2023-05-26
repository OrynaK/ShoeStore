package ua.nure.shoestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Cart {
    private long id;
    private long userId;
    private List<ShoeOrder> shoesInCart;

    public Cart() {
        shoesInCart = new ArrayList<>();
    }

    public void addShoe(ShoeOrder shoeOrder) {
        shoesInCart.add(shoeOrder);
    }
}
