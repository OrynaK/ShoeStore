package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.entity.*;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class OrderDAOImpl implements OrderDAO {
    private static final String INSERT_ORDER = "INSERT INTO `order` (address_id, datetime, status) VALUES (?, DEFAULT, DEFAULT)";
    private static final String UPDATE_STATUS = "UPDATE `order` SET status=? WHERE id=?";
    private static final String INSERT_SHOES_ORDER = "INSERT INTO `shoe_order` (order_id, shoe_id, price, amount) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ORDER_USER = "INSERT INTO `user_order` (order_id, user_id, description, datetime) VALUES (?, ?, DEFAULT, DEFAULT)";
    private static final String GET_ORDER_BY_ROLE = "SELECT * from `order` WHERE status=?";
    private static final String GET_ORDERS = "SELECT * from `order`";
    private static final String GET_ORDER_BY_ID = "SELECT * from `order` WHERE id=?";
    private static final String GET_SHOE_ORDER = "SELECT * FROM `shoe_order` WHERE order_id=?";
    private static final String GET_USER_ORDER = "SELECT * FROM `user_order` WHERE order_id=?";
    private static final String GET_ORDER_FROM_USER_ORDER = "SELECT order_id FROM `user_order` WHERE user_id=?";
    private static final String GET_ROLE = "SELECT role FROM `user` WHERE id=?";

    private final ConnectionManager connectionManager;

    public OrderDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public List<Order> getOrdersByRole(Role role) {
        List<Order> orders = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_ORDER_BY_ROLE)) {
                int k = 0;
                if (role == Role.ADMIN) {
                    orders = findAll();
                    return orders;
                }
                String status = switch (role) {
                    case WAREHOUSE -> "accepted";
                    case PACKER -> "compiled";
                    case COURIER -> "ready_for_sending";
                    default -> null;
                };
                ps.setString(++k, status);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        boolean isChosen = false;
                        Order order = mapOrder(rs);
                        try (PreparedStatement prs = con.prepareStatement(GET_USER_ORDER)) {
                            int l = 0;
                            prs.setLong(++l, order.getId());
                            UserOrder userOrder;
                            try (ResultSet resultSet = prs.executeQuery()) {
                                while (resultSet.next()) {
                                    userOrder = mapUserOrder(resultSet);
                                    Role role1 = null;
                                    try (PreparedStatement preparedStatement = con.prepareStatement(GET_ROLE)) {
                                        int m = 0;
                                        preparedStatement.setLong(++m, userOrder.getUserId());
                                        try (ResultSet resultSet1 = preparedStatement.executeQuery()) {
                                            while (resultSet1.next()) {
                                                role1 = getRole(userOrder.getUserId());
                                                if (role1 == role) {
                                                    isChosen = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    order.putUser(role1, userOrder);
                                }
                            }
                        }
                        if (!isChosen) {
                            getShoeOrder(order, con);
                            orders.add(order);
                        }
                    }
                    return orders;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_ORDER_FROM_USER_ORDER)) {
                int k = 0;
                ps.setLong(++k, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        orders.add(findById(rs.getLong(1)));
                    }
                    return orders;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeStatus(Long orderId, OrderStatus status) {
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(UPDATE_STATUS)) {
                int k = 0;
                ps.setString(++k, String.valueOf(status));
                ps.setLong(++k, orderId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (Statement ps = con.createStatement()) {
                try (ResultSet rs = ps.executeQuery(GET_ORDERS)) {
                    while (rs.next()) {
                        Order order = mapOrder(rs);
                        try (PreparedStatement prs = con.prepareStatement(GET_USER_ORDER)) {
                            int l = 0;
                            prs.setLong(++l, order.getId());
                            UserOrder userOrder;
                            try (ResultSet resultSet = prs.executeQuery()) {
                                while (resultSet.next()) {
                                    userOrder = mapUserOrder(resultSet);
                                    Role role1 = null;
                                    try (PreparedStatement preparedStatement = con.prepareStatement(GET_ROLE)) {
                                        int m = 0;
                                        preparedStatement.setLong(++m, userOrder.getUserId());
                                        try (ResultSet resultSet1 = preparedStatement.executeQuery()) {
                                            while (resultSet1.next()) {
                                                role1 = getRole(userOrder.getUserId());
                                            }
                                        }
                                    }
                                    order.putUser(role1, userOrder);
                                }
                            }
                        }
                        getShoeOrder(order, con);
                        orders.add(order);
                    }

                    return orders;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getShoeOrder(Order order, Connection con) throws SQLException {
        try (PreparedStatement prs = con.prepareStatement(GET_SHOE_ORDER)) {
            int l = 0;
            prs.setLong(++l, order.getId());
            try (ResultSet resultSet = prs.executeQuery()) {
                while (resultSet.next()) {
                    try (PreparedStatement prst = con.prepareStatement("SELECT name, size, color FROM shoe WHERE id=?")) {
                        int b = 0;
                        prst.setLong(++b,resultSet.getLong("shoe_id"));
                        try (ResultSet rs = prst.executeQuery()) {
                            while (rs.next()) {
                                order.addShoe(mapShoeOrder(resultSet, rs));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public long insert(Order order) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = connectionManager.getConnection(false);
            st = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS); // insert order
            st.setLong(1, order.getAddress().getId());
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();

            st = conn.prepareStatement(INSERT_SHOES_ORDER); // insert shoes
            if (generatedKeys.next()) { // if we have order id
                order.setId(generatedKeys.getLong(1));
                for (ShoeOrder shoeOrder : order.getShoesInOrder()) {
                    setShoeOrder(order.getId(), shoeOrder, st);
                    st.addBatch();
                }
                st.executeBatch();

                st = conn.prepareStatement(INSERT_ORDER_USER); // insert CLIENT
                setClient(order, st);
                st.executeUpdate();
            }
            conn.commit();
            return order.getId();
        } catch (Exception e) {
            ConnectionManager.rollback(conn);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(st, conn);
        }
    }

    private static void setShoeOrder(long orderId, ShoeOrder shoeOrder, PreparedStatement st) throws SQLException {
        int k = 0;
        st.setLong(++k, orderId);
        st.setLong(++k, shoeOrder.getShoeId());
        st.setBigDecimal(++k, shoeOrder.getPrice());
        st.setInt(++k, shoeOrder.getAmount());
    }

    private static void setClient(Order order, PreparedStatement st) throws SQLException {
        st.setLong(1, order.getId());
        st.setLong(2, order.getUsersInOrder().get(Role.CLIENT).getUserId());
    }

    @Override
    public Order findById(long orderId) {
        Order order = new Order();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_ORDER_BY_ID)) {
                int k = 0;
                ps.setLong(++k, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        order = mapOrder(rs);
                        try (PreparedStatement prs = con.prepareStatement(GET_USER_ORDER)) {
                            int l = 0;
                            prs.setLong(++l, order.getId());
                            UserOrder userOrder;
                            try (ResultSet resultSet = prs.executeQuery()) {
                                while (resultSet.next()) {
                                    userOrder = mapUserOrder(resultSet);
                                    Role role1 = null;
                                    try (PreparedStatement preparedStatement = con.prepareStatement(GET_ROLE)) {
                                        int m = 0;
                                        preparedStatement.setLong(++m, userOrder.getUserId());
                                        try (ResultSet resultSet1 = preparedStatement.executeQuery()) {
                                            while (resultSet1.next()) {
                                                role1 = getRole(userOrder.getUserId());
                                            }
                                        }
                                    }
                                    order.putUser(role1, userOrder);
                                }
                            }
                        }
                        getShoeOrder(order, con);
                    }
                    return order;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Role getRole(Long id) {
        Role role = null;
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_ROLE)) {
                int k = 0;
                ps.setLong(++k, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        role = Role.valueOf(resultSet.getString("role").toUpperCase());
                    }
                    return role;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        LocalDateTime dateTime = rs.getTimestamp("datetime").toLocalDateTime();
        o.setDate(dateTime.toLocalDate());
        o.setTime(dateTime.toLocalTime());
        o.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
        o.setTotalCost(rs.getBigDecimal("total_cost"));
        return o;
    }

    private ShoeOrder mapShoeOrder(ResultSet resultSet, ResultSet rs) throws SQLException {
        ShoeOrder shoeOrder = new ShoeOrder();
        shoeOrder.setShoeId(resultSet.getLong("shoe_id"));
        shoeOrder.setPrice(resultSet.getBigDecimal("price"));
        shoeOrder.setAmount(resultSet.getInt("amount"));
        shoeOrder.setName(rs.getString("name"));
        shoeOrder.setSize(rs.getBigDecimal("size"));
        shoeOrder.setColor(rs.getString("color"));
        return shoeOrder;
    }

    private UserOrder mapUserOrder(ResultSet rs) throws SQLException {
        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(rs.getLong("user_id"));
        userOrder.setDescription(rs.getString("description"));
        LocalDateTime dateTime = rs.getTimestamp("datetime").toLocalDateTime();
        userOrder.setDate(dateTime.toLocalDate());
        userOrder.setTime(dateTime.toLocalTime());

        return userOrder;
    }

    @Override
    public void update(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
