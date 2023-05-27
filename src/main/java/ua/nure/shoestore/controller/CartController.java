package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.CartShoeDTO;
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

    @PostMapping("/deleteShoeFromCart")
    public void deleteShoeFromCart(@RequestParam long userId, @RequestParam long shoeId) {
        cartService.deleteShoeFromCart(userId, shoeId);
    }

    @PostMapping(value = "/addShoeToCart")
    public void addShoeToCart(@RequestBody CartShoeDTO cartShoeDTO) {
        long userId = cartShoeDTO.getUserId();
        cartService.addShoeToCart(userId,
                new ShoeOrder(cartShoeDTO.getShoeId(), cartShoeDTO.getPrice(), cartShoeDTO.getAmount()));
    }

    @PostMapping(value = "/createCart")
    public void createCart(@RequestParam("cart") Cart cart) {
        cartService.createCart(cart);
    }

}
