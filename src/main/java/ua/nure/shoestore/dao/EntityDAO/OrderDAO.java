package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.dao.CRUDRepository;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dto.BestUserDTO;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;

import java.util.List;

public interface OrderDAO extends CRUDRepository<Order> {
    List<Order> getOrdersByRole(Role role);
    List<Order> getOrdersByUserId(Long userId);
    void changeStatus(Long orderId, OrderStatus status) throws DBException;
    List<BestUserDTO> test();
}
