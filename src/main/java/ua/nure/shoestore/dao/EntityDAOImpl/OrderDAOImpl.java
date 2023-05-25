package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.ShoeOrder;
import ua.nure.shoestore.entity.enums.Role;

import java.sql.*;

public class OrderDAOImpl implements OrderDAO {
    private static final String INSERT_ORDER = "INSERT INTO `order` (address_id, datetime, status) VALUES (?, DEFAULT, DEFAULT)";
    private static final String INSERT_SHOES_ORDER = "INSERT INTO `shoe_order` (order_id, shoe_id, price, amount) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ORDER_USER = "INSERT INTO `user_order` (order_id, user_id, description, datetime) VALUES (?, ?, DEFAULT, DEFAULT)";
    private final ConnectionManager connectionManager;

    public OrderDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public void create(Order order) {
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
                order.setOrderId(generatedKeys.getLong(1));
                for (ShoeOrder shoeOrder : order.getShoesInOrder()) {
                    setShoeOrder(order.getOrderId(), shoeOrder, st);
                    st.addBatch();
                }
                st.executeBatch();

                st = conn.prepareStatement(INSERT_ORDER_USER); // insert CLIENT
                setClient(order, st);
                st.executeUpdate();
            }
            conn.commit();
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
        st.setLong(1, order.getOrderId());
        st.setLong(2, order.getUsersInOrder().get(Role.CLIENT).getUserId());
    }

    @Override
    public Order getById(long orderId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Order order) {
        throw new UnsupportedOperationException();
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