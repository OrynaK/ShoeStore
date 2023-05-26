package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.dao.CRUDRepository;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.ShoeOrder;


public interface CartDAO extends CRUDRepository<Cart> {
    Cart findByUserId(long userId);

    void insertShoeToCart(long cartId, ShoeOrder orderShoe);

    void deleteShoeFromCart(long cartId, long shoeId);
}
