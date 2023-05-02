package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.forms.UpdateForm;

@Service
public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO){this.userDAO=userDAO;}

    public User updateInfo(UpdateForm updateForm) {
       return userDAO.update(updateForm);
    }

    public void updateRole(long user_id, Role role) {
        userDAO.updateRole(user_id, role);
    }
}
