package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.CartDAO;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.dao.EntityDAOImpl.CartDAOImpl;
import ua.nure.shoestore.dao.EntityDAOImpl.ShoeDAOImpl;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.ShoeOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartDAO cartDAO;
    private final ShoeDAO shoeDAO;

    public CartService(CartDAO cartDAO, ShoeDAO shoeDAO) {
        this.shoeDAO = shoeDAO;
        this.cartDAO = cartDAO;
    }

    public long createCart(Cart cart) {
        return cartDAO.insert(cart);
    }

    public void addShoeToCart(long userId, ShoeOrder shoeCart) {
        cartDAO.insertShoeToCart(cartDAO.findByUserId(userId).getId(), shoeCart);
    }

    public void deleteShoeFromCart(long userId, long shoeId) {
        cartDAO.deleteShoeFromCart(cartDAO.findByUserId(userId).getId(), shoeId);
    }

    public List<Shoe> getShoesByUserId(long userId) {
        List<ShoeOrder> shoesInCart = cartDAO.findByUserId(userId).getShoesInCart();
        List<Shoe> shoes = new ArrayList<>();
        for (ShoeOrder shoeOrder : shoesInCart) {
            Shoe shoe = shoeDAO.findById(shoeOrder.getShoeId());
            shoe.setPrice(shoeOrder.getPrice());
            shoe.setAmount(shoeOrder.getAmount());
            shoes.add(shoe);
        }
        return shoes;
    }
}