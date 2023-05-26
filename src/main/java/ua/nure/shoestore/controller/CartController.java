package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.shoestore.entity.Shoe;
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
}
