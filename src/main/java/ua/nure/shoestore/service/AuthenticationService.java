package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.CartDAO;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;


@Service
public class AuthenticationService {
    private final UserDAO userDAO;
    private final CartDAO cartDAO;

    public AuthenticationService(UserDAO userDAO, CartDAO cartDAO) {
        this.userDAO = userDAO;
        this.cartDAO = cartDAO;
    }

    public User addUser(User user) {
        long userId = userDAO.insert(user);
        if (user.getRole().equals(Role.CLIENT)) {
            cartDAO.insert(new Cart(userId));
        }
        user.setId(userId);
        return user;
    }

    public User logIn(String email, String password) {
        return userDAO.getUser(email, password);
    }
}
