package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.dao.CRUDRepository;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.ShoeOrder;

import java.sql.SQLException;


public interface CartDAO extends CRUDRepository<Cart> {
    Cart findByUserId(long userId);

    void insertShoeToCart(long cartId, ShoeOrder orderShoe) throws SQLException;

    void deleteShoeFromCart(long cartId, long shoeId);

    void deleteAllShoesFromCart(long cartId);
}
