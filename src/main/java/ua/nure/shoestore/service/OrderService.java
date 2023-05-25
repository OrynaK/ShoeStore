package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.AddressDAO;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.entity.Order;

@Service
public class OrderService {
    private final OrderDAO orderDAO;
    private final AddressDAO addressDAO;

    public OrderService(OrderDAO orderDAO, AddressDAO addressDAO) {
        this.orderDAO = orderDAO;
        this.addressDAO = addressDAO;
    }

    public long addOrder(Order order) {
        long addressId = addressDAO.insert(order.getAddress());
        order.getAddress().setId(addressId);
        return orderDAO.insert(order);
    }
}
