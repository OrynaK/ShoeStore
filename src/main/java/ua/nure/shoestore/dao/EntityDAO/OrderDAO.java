package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.dao.CRUDRepository;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.enums.Role;

import java.util.List;

public interface OrderDAO extends CRUDRepository<Order> {
    public List<Order> getOrdersByRole(Role role);
    public List<Order> getOrdersByUserId(Long userId);
}
