package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.entity.Order;

public class OrderDAOImpl implements OrderDAO {
    private ConnectionManager connectionManager;

    public OrderDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public void create(Order order) {

    }

    @Override
    public Order getById(long orderId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Order order) {
        throw new UnsupportedOperationException();
    }

}
