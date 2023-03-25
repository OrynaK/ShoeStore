package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.entity.User;

public interface UserDAO {
    public void addUser(User user);
    public User getUser(String email, String password);
}
