package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.CartShoeDTO;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.ShoeOrder;
import ua.nure.shoestore.service.CartService;

import java.sql.SQLIntegrityConstraintViolationException;
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
    public ResponseEntity<String> addShoeToCart(@RequestBody CartShoeDTO cartShoeDTO) {
        long userId = cartShoeDTO.getUserId();
        try {
            cartService.addShoeToCart(userId,
                    new ShoeOrder(cartShoeDTO.getShoeId(), cartShoeDTO.getPrice(), cartShoeDTO.getAmount()));
            return ResponseEntity.ok().build();
        } catch (SQLIntegrityConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"error\": \"The shoes have already been added to the cart.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred.\"}");
        }
    }


}
