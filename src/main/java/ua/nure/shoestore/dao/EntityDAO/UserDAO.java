package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;

public interface UserDAO{
    User getUser(String email, String password);
    void update(User user);
    void updateRole(long user_id, Role role);
    void add(User user);
}
