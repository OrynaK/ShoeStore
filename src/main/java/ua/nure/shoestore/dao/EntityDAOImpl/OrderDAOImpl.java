package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.OrderShoe;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class OrderDAOImpl implements OrderDAO {
    private final ConnectionManager connectionManager;
    private static final String INSERT_ORDER = "INSERT INTO `order` (date, time, status, address_id) VALUES (?, ?, DEFAULT, ?)";
    private static final String INSERT_SHOES_ORDER = "INSERT INTO `shoes_order` (order_id, shoe_id, shoes_amount, shoe_price) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ORDER_USER = "INSERT INTO `order_user` (order_id, user_id) VALUES (?, ?)";

    public OrderDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public void create(Order order) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = connectionManager.getConnection(false);
            st = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            setOrder(order, st);
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();

            st = conn.prepareStatement(INSERT_SHOES_ORDER);
            setShoesOrder(order, st, generatedKeys);

            st = conn.prepareStatement(INSERT_ORDER_USER);
            setUsers(order, st);
            st.executeBatch();

            conn.commit();
        } catch (Exception e) {
            ConnectionManager.rollback(conn);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(st, conn);
        }
    }

    private static void setOrder(Order order, PreparedStatement st) throws SQLException {
        int k = 0;
        st.setDate(++k, java.sql.Date.valueOf(order.getDate()));
        st.setTime(++k, java.sql.Time.valueOf(order.getTime()));
        st.setLong(++k, order.getAddress().getAddressId());
    }

    private static void setShoesOrder(Order order, PreparedStatement st, ResultSet generatedKeys) throws SQLException {
        int k;
        while (generatedKeys.next()) {
            order.setOrderId(generatedKeys.getLong(1));
            for (OrderShoe orderShoe : order.getShoesInOrder()) {
                k = 0;
                st.setLong(++k, order.getOrderId());
                st.setLong(++k, orderShoe.getShoeId());
                st.setInt(++k, orderShoe.getAmount());
                st.setBigDecimal(++k, orderShoe.getPrice());
                st.addBatch();
            }
            st.executeBatch();
        }
    }

    private static void setUsers(Order order, PreparedStatement st) throws SQLException {
        st.setLong(1, order.getOrderId());
        st.setLong(2, order.getClientId());
        st.addBatch();

        st.setLong(1, order.getOrderId());
        st.setLong(2, order.getAdminId());
        st.addBatch();

        st.setLong(1, order.getOrderId());
        st.setLong(2, order.getPackerId());
        st.addBatch();

        st.setLong(1, order.getOrderId());
        st.setLong(2, order.getWarehouseId());
        st.addBatch();

        st.setLong(1, order.getOrderId());
        st.setLong(2, order.getCourierId());
        st.addBatch();
    }

    public static void main(String[] args) {
        // test
        OrderDAO orderDAO = new OrderDAOImpl(new DAOConfig("MySQL", "jdbc:mysql://localhost:3306/shoe_store?sslMode=DISABLED&serverTimezone=UTC", "root", "root"));
        Order order = new Order();
        order.setClientId(14);
        order.setAdminId(15);
        order.setPackerId(16);
        order.setWarehouseId(17);
        order.setCourierId(18);
        order.setDate(LocalDate.now());
        order.setTime(LocalTime.now());
        order.setAddress(new AddressDAOImpl(new DAOConfig("MySQL", "jdbc:mysql://localhost:3306/shoe_store?sslMode=DISABLED&serverTimezone=UTC", "root", "root")).getById(1));
        order.getShoesInOrder().add(new OrderShoe(37, 2, BigDecimal.valueOf(1000)));
        order.getShoesInOrder().add(new OrderShoe(38, 1, BigDecimal.valueOf(2000)));
        orderDAO.create(order);
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
