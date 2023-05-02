package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.forms.UpdateForm;

public interface UserDAO{
    User getUser(String email, String password);
    User update(UpdateForm updateForm);
    void updateRole(long user_id, Role role);
    void add(User user);
}
