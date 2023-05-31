package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dao.EntityDAO.AddressDAO;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.dao.EntityDAO.WorkerDAO;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(rollbackFor = SQLException.class)
public class OrderService {
    private final OrderDAO orderDAO;
    private final AddressDAO addressDAO;


    public OrderService(OrderDAO orderDAO, AddressDAO addressDAO, WorkerDAO workerDAO, UserDAO userDAO) {
        this.orderDAO = orderDAO;
        this.addressDAO = addressDAO;
    }

    @Transactional(rollbackFor = DBException.class)
    public long makeOrder(Order order) throws DBException {
        try {
            long addressId = addressDAO.insert(order.getAddress());
            order.getAddress().setId(addressId);
            return orderDAO.insert(order);
        } catch (DBException e) {
            throw new DBException("Unable to commit changes in the DB", e);
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderDAO.findAll();
        for (Order order : orders) {
            order.setAddress(addressDAO.getAddressByOrder(order.getId()));
        }
        return orders;
    }

    public List<Order> getOrdersByRole(Role role) {
        List<Order> orders = orderDAO.getOrdersByRole(role);
        for (Order order : orders) {
            order.setAddress(addressDAO.getAddressByOrder(order.getId()));
        }
        return orders;
    }

    public List<Order> getOrderByUserId(Long userId) {
        List<Order> orders = orderDAO.getOrdersByUserId(userId);
        for (Order order : orders) {
            order.setAddress(addressDAO.getAddressByOrder(order.getId()));
        }
        return orders;
    }

}
