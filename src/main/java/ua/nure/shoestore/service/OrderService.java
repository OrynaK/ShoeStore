package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dao.EntityDAO.*;
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
    private final WorkerDAO workerDAO;
    private final UserDAO userDAO;
    private final ShoeDAO shoeDAO;

    public OrderService(OrderDAO orderDAO, AddressDAO addressDAO, WorkerDAO workerDAO, UserDAO userDAO, ShoeDAO shoeDAO) {
        this.orderDAO = orderDAO;
        this.addressDAO = addressDAO;
        this.workerDAO = workerDAO;
        this.userDAO = userDAO;
        this.shoeDAO = shoeDAO;
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

    public boolean setWorker(Long orderId, Long userId) {
        List<Long> ids = workerDAO.getIdFromUserOrder(orderId);
        Role role = userDAO.findById(userId).getRole();
        boolean isChosen = false;
        for (Long id : ids) {
            if (role == userDAO.findById(id).getRole()) {
                isChosen = true;
            }
        }
        if (!isChosen) {
            workerDAO.setWorker(orderId, userId);
        }
        return isChosen;
    }

    public boolean isSetAdmin(Long orderId) {
        List<Long> ids = workerDAO.getIdFromUserOrder(orderId);
        for (Long id : ids) {
            if (userDAO.findById(id).getRole() == Role.ADMIN) {
                return true;
            }
        }
        return false;
    }

    @Transactional(rollbackFor = DBException.class)
    public void changeStatus(Long orderId, Long userId, OrderStatus status, String description) throws DBException {
        try {
            orderDAO.changeStatus(orderId, status);
            workerDAO.setDescription(orderId, userId, description);
        } catch (DBException e) {
            throw new DBException("Unable to commit changes in the DB", e);
        }
    }
}
