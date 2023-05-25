package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.ShoeOrder;
import ua.nure.shoestore.entity.UserOrder;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;

import java.sql.*;
import java.util.*;

public class OrderDAOImpl implements OrderDAO {
    private static final String INSERT_ORDER = "INSERT INTO `order` (address_id, datetime, status) VALUES (?, DEFAULT, DEFAULT)";
    private static final String INSERT_SHOES_ORDER = "INSERT INTO `shoe_order` (order_id, shoe_id, price, amount) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ORDER_USER = "INSERT INTO `user_order` (order_id, user_id, description, datetime) VALUES (?, ?, DEFAULT, DEFAULT)";
    private static final String GET_ORDERS_BY_ROLE = "SELECT * from order WHERE status=?";
    private AddressDAOImpl addressDAO;
    private UserDAOImpl userDAO;
    private final ConnectionManager connectionManager;

    public OrderDAOImpl(DAOConfig config, AddressDAOImpl addressDAO, UserDAOImpl userDAO) {
        this.addressDAO = addressDAO;
        this.userDAO = userDAO;
        connectionManager = new ConnectionManager(config);
    }

//    public List<Order> getOrdersByRole(Role role) {
//        List<Order> orders = new ArrayList<>();
//        try (Connection con = connectionManager.getConnection()) {
//            try (PreparedStatement ps = con.prepareStatement(GET_ORDERS_BY_ROLE)) {
//                int k = 0;
//                String status = null;
//                switch (role) {
//                    case ADMIN:
//                        status = "processing";
//                        break;
//                    case WAREHOUSE:
//                        status = "accepted";
//                        break;
//                    case PACKER:
//                        status = "compiled";
//                        break;
//                    case COURIER:
//                        status = "ready_for_sending";
//                        break;
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

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

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setDate(rs.getDate("date").toLocalDate());
        o.setTime(rs.getTime("time").toLocalTime());
        o.setStatus(OrderStatus.valueOf(rs.getString("status")));
        o.setTotalCost(rs.getBigDecimal("totalCost"));
        return o;
    }

    private Order mapAddress(Order order, ResultSet rs) throws SQLException {
        order.setAddress(addressDAO.mapAddress(rs));
        return order;
    }

    private Order mapShoeOrder(Order order, ResultSet rs) throws SQLException {
        List<ShoeOrder> shoeOrders = new ArrayList<>();
        while (rs.next()) {
            ShoeOrder shoeOrder = new ShoeOrder();
            shoeOrder.setShoeId(rs.getLong("shoe_id"));
            shoeOrder.setPrice(rs.getBigDecimal("price"));
            shoeOrder.setAmount(rs.getInt("amount"));
            shoeOrders.add(shoeOrder);
        }
        order.setShoesInOrder(shoeOrders);
        return order;
    }

    private Order mapUserOrder(Order order, ResultSet rs) throws SQLException {
        Map<Role, UserOrder> userOrders = new EnumMap<>(Role.class);

        while (rs.next()) {
            UserOrder userOrder = null;
            userOrder.setUserId(rs.getLong("user_id"));
            userOrder.setDescription(rs.getString("description"));
            userOrder.setDate(rs.getDate("date").toLocalDate());
            userOrder.setTime(rs.getTime("time").toLocalTime());

            userOrders.put(userDAO.findById(userOrder.getUserId()).getRole(), userOrder);
        }

        order.setUsersInOrder(userOrders);
        return order;
    }

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