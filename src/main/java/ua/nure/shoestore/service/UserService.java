package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.dto.UpdateDTO;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;

    public List<User> getUsers() {
        return this.userDAO.findAll();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User updateInfo(UpdateDTO updateDTO) {
        return userDAO.update(updateDTO);
    }

    public void updateRole(long userId, Role role) {
        userDAO.updateRole(userId, role);
    }
}
