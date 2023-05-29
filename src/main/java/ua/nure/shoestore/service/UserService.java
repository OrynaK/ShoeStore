package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.CartDAO;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.dto.UpdateDTO;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final CartDAO cartDAO;

    public UserService(UserDAO userDAO, CartDAO cartDAO) {
        this.cartDAO = cartDAO;
        this.userDAO = userDAO;
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

    public List<User> getUsers() {
        return this.userDAO.findAll();
    }


    public User updateInfo(UpdateDTO updateDTO) {
        return userDAO.update(updateDTO);
    }

    public void updateRole(long userId, Role role) {
        userDAO.updateRole(userId, role);
    }
}
