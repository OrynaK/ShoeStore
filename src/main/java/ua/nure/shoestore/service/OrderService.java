package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.AddressDAO;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.enums.Role;

import java.util.List;

@Service
public class OrderService {
    private final OrderDAO orderDAO;
    private final AddressDAO addressDAO;

    public OrderService(OrderDAO orderDAO, AddressDAO addressDAO) {
        this.orderDAO = orderDAO;
        this.addressDAO = addressDAO;
    }

    public long makeOrder(Order order) {
        long addressId = addressDAO.insert(order.getAddress());
        order.getAddress().setId(addressId);
        return orderDAO.insert(order);
    }

    public List<Order> getOrdersByRole(Role role) {
        List<Order> orders = orderDAO.getOrdersByRole(role);
        for (Order order : orders) {
            order.setAddress(addressDAO.getAddressByOrder(order.getId()));
        }
        return orders;
    }
}
