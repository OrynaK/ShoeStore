package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.User;


@Service
public class AuthenticationService {
    private UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addUser(User user) {
        userDAO.add(user);
    }

    public User logIn(String email, String password) {
        return userDAO.getUser(email, password);
    }
}
