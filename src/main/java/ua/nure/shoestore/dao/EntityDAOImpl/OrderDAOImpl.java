package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class OrderDAOImpl implements OrderDAO {
    private final String url;
    private final Properties dbProps = new Properties();

    public OrderDAOImpl(DAOConfig config) {
        url = config.getUrl();
        dbProps.setProperty("user", config.getUser());
        dbProps.setProperty("password", config.getPassword());
    }

    @Override
    public void create(Order order) {

    }

    private static void rollback(Connection conn) {
        try {
            Objects.requireNonNull(conn).rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void close(AutoCloseable ac) {
        try {
            Objects.requireNonNull(ac).close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
