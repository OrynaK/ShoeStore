package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.CartDAO;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.ShoeOrder;

import java.util.List;

@Service
public class CartService {
    private final CartDAO cartDAO;

    public CartService(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }


    public void addShoeToCart(long cartId, ShoeOrder shoeOrder) {
        cartDAO.insertShoeToCart(cartId, shoeOrder);
    }

    public void deleteShoeFromCart(long cartId, long shoeId) {
        cartDAO.deleteShoeFromCart(cartId, shoeId);
    }

    public List<ShoeOrder> getShoesByUserId(long userId) {
        return cartDAO.findByUserId(userId).getShoesInCart();
    }
}
