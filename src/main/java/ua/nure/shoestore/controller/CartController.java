package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.shoestore.service.CartService;

@RestController
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;

}
