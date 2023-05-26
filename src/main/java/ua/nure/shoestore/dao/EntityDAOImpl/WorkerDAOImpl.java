package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.WorkerDAO;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAOImpl implements WorkerDAO {
    private final String INSERT_USER_ORDER = "INSERT INTO user_order (order_id, user_id) VALUES (?, ?)";
    private final ConnectionManager connectionManager;

    public WorkerDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public void setWorker(Long orderId, Long userId) {

    }

    @Override
    public void changeStatus(Long orderId, Long userId, OrderStatus status, String description) {

    }


}
