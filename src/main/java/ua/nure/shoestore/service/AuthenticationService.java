package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.CartDAO;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.User;


@Service
public class AuthenticationService {
    private final UserDAO userDAO;
    private final CartDAO cartDAO;
    public AuthenticationService(UserDAO userDAO, CartDAO cartDAO) {
        this.userDAO = userDAO;
        this.cartDAO = cartDAO;
    }

    public void addUser(User user, Cart cart) {
        long userId = userDAO.insert(user);
        cart.setUserId(userId);
        cartDAO.insert(cart);
    }

    public User logIn(String email, String password) {
        return userDAO.getUser(email, password);
    }
}
