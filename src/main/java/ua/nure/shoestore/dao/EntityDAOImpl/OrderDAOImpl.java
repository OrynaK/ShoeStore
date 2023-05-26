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
    private static final String INSERT_SHOES_ORDER = "INSERT INTO `shoe_order` (order_id, shoe_id, price, amount) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ORDER_USER = "INSERT INTO `user_order` (order_id, user_id, description, datetime) VALUES (?, ?, DEFAULT, DEFAULT)";
    private static final String GET_ORDERS_BY_ROLE = "SELECT * from `order` WHERE status=?";
    private static final String GET_SHOE_ORDER = "SELECT * FROM `shoe_order` WHERE order_id=?";
    private static final String GET_USER_ORDER = "SELECT * FROM `user_order` WHERE order_id=?";
    private static final String GET_ROLE = "SELECT role FROM `user` WHERE id=?";

    private final ConnectionManager connectionManager;

    public OrderDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public List<Order> getOrdersByRole(Role role) {
        List<Order> orders = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_ORDERS_BY_ROLE)) {
                int k = 0;
                String status = switch (role) {
                    case ADMIN -> "processing";
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
                            try (PreparedStatement prs = con.prepareStatement(GET_SHOE_ORDER)) {
                                int l = 0;
                                prs.setLong(++l, order.getId());
                                try (ResultSet resultSet = prs.executeQuery()) {
                                    while (resultSet.next()) {
                                        order.addShoe(mapShoeOrder(resultSet));
                                    }
                                }
                            }
                            orders.add(mapOrder(rs));
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
    public void update(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order findById(long orderId) {
        throw new UnsupportedOperationException();
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

    private ShoeOrder mapShoeOrder(ResultSet rs) throws SQLException {
        ShoeOrder shoeOrders = new ShoeOrder();
        ShoeOrder shoeOrder = new ShoeOrder();
        shoeOrder.setShoeId(rs.getLong("shoe_id"));
        shoeOrder.setPrice(rs.getBigDecimal("price"));
        shoeOrder.setAmount(rs.getInt("amount"));
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

    // userOrders.put(userDAO.findById(userOrder.getUserId()).getRole(), userOrder);


/*    public static void main(String[] args) throws SQLException {
        // test
        OrderDAOImpl orderDAO = new OrderDAOImpl(new DAOConfig("MySQL", "jdbc:mysql://localhost:3306/shoe_store?sslMode=DISABLED&serverTimezone=UTC", "root", "root"));
        Order order = new Order();
        order.putUser(Role.CLIENT, new UserOrder(20));
        order.setAddress(new AddressDAOImpl(new DAOConfig("MySQL", "jdbc:mysql://localhost:3306/shoe_store?sslMode=DISABLED&serverTimezone=UTC", "root", "root")).getById(1));
        order.getShoesInOrder().add(new ShoeOrder(37, BigDecimal.valueOf(1000), 2));
        order.getShoesInOrder().add(new ShoeOrder(38, BigDecimal.valueOf(2000), 1));
        orderDAO.create(order);
    }*/
}