package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.ShoeOrder;
import ua.nure.shoestore.service.CartService;

import java.util.List;

@RestController
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    public List<Shoe> getShoesByUserId(@RequestBody long userId) {
        return cartService.getShoesByUserId(userId);
    }

    @PostMapping(value = "/addShoeToCart")
    public void showShoePage(@RequestParam("cartId") Long cartId, @RequestParam("shoeOrder") ShoeOrder shoeOrder) {
        cartService.addShoeToCart(cartId, shoeOrder);
    }
    @PostMapping(value = "/createCart")
    public void createCart(@RequestParam("cart") Cart cart) {
        cartService.createCart(cart);
    }

}
