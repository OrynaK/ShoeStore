package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.dao.CRUDRepository;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.forms.UpdateForm;

public interface UserDAO extends CRUDRepository<User> {
    User getUser(String email, String password);
    void updateRole(long userId, Role role);
    User update(UpdateForm updateForm);
}
