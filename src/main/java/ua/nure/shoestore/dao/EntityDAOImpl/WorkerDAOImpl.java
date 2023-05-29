package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.WorkerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAOImpl implements WorkerDAO {
    private static final String INSERT_USER_ORDER = "INSERT INTO user_order (order_id, user_id) VALUES (?, ?)";
    private static final String GET_USER_ORDER = "SELECT user_id FROM user_order WHERE order_id=?";
    private static final String SET_DESCRIPTION = "UPDATE user_order SET description=? WHERE order_id=? and user_id=?";
    private final ConnectionManager connectionManager;

    public WorkerDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public void setWorker(Long orderId, Long userId) {
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(INSERT_USER_ORDER)) {
                int k = 0;
                ps.setLong(++k, orderId);
                ps.setLong(++k, userId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> getIdFromUserOrder(Long orderId) {
        List<Long> ids = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_USER_ORDER)) {
                int k = 0;
                ps.setLong(++k, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ids.add(rs.getLong(1));
                    }
                }
                return ids;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setDescription(Long orderId, Long userId, String description) {
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(SET_DESCRIPTION)) {
                int k = 0;
                ps.setString(++k, description);
                ps.setLong(++k, orderId);
                ps.setLong(++k, userId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
