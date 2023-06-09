package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dao.EntityDAO.CartDAO;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.dto.UpdateDTO;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(rollbackFor = SQLException.class)
public class UserService {
    private final UserDAO userDAO;
    private final CartDAO cartDAO;

    public UserService(UserDAO userDAO, CartDAO cartDAO) {
        this.cartDAO = cartDAO;
        this.userDAO = userDAO;
    }

    @Transactional(rollbackFor = DBException.class)
    public User addUser(User user) throws DBException {
        try {
            long userId = userDAO.insert(user);
            if (user.getRole().equals(Role.CLIENT)) {
                cartDAO.insert(new Cart(userId));
            }
            user.setId(userId);
            return user;
        } catch (DBException e) {
            throw new DBException("Unable to commit changes in the DB", e);
        }
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
