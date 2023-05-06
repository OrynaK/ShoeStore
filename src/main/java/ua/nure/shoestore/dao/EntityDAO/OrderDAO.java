package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.entity.Order;

public interface OrderDAO {

    void create(Order order);

    Order getById(long orderId);

    void delete(Order order);

}
