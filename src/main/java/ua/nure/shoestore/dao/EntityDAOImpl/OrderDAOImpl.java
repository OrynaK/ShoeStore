package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.dto.BestUserDTO;
import ua.nure.shoestore.dto.ShoeCardDTO;
import ua.nure.shoestore.entity.*;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.entity.enums.Season;
import ua.nure.shoestore.entity.enums.Sex;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectionManager.getConnection(false);
            ps = conn.prepareStatement(GET_ORDER_BY_ROLE);
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
                    try (PreparedStatement prs = conn.prepareStatement(GET_USER_ORDER)) {
                        int l = 0;
                        prs.setLong(++l, order.getId());
                        UserOrder userOrder;
                        try (ResultSet resultSet = prs.executeQuery()) {
                            while (resultSet.next()) {
                                userOrder = mapUserOrder(resultSet);
                                Role role1 = null;
                                try (PreparedStatement preparedStatement = conn.prepareStatement(GET_ROLE)) {
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
                        getShoeOrder(order, conn);
                        orders.add(order);
                    }
                }
            }

            conn.commit();
            return orders;
        } catch (Exception e) {
            ConnectionManager.rollback(conn);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(ps, conn);
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
    public void changeStatus(Long orderId, OrderStatus status) throws DBException {
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(UPDATE_STATUS)) {
                int k = 0;
                ps.setString(++k, String.valueOf(status));
                ps.setLong(++k, orderId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(e);
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
                        prst.setLong(++b, resultSet.getLong("shoe_id"));
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

    private static void setShoeOrder(long orderId, ShoeOrder shoeOrder, PreparedStatement st) throws
            SQLException {
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
        userOrder.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());
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

    @Override
    //змінити тип повертаємого об'єкта тут, та в класі OrderDAO
    //        |
    //        |
    //       \/
    public List<BestUserDTO> test() {
        // приклад з дто (вивести відсортованих користувачів за їх витратами в магазині)
        // + коротко як працює
        // створюємо лист з нашими об'єктами
        // якщо треба вивести дані з різних таблиць створюємо ДТО (в папке dto створюємо новий клас, приклад для BestUserDTO там же)
        // і цей клас вписуємо в List<?DTO>
        List<BestUserDTO> usersList = new ArrayList<>();
        // міняємо запит
        String query = "SELECT u.id, u.name, u.surname,so.order_id, SUM(so.price * so.amount) AS total_spent\n" +
                "FROM `user` AS u \n" +
                "    INNER JOIN user_order AS uo ON uo.user_id = u.id\n" +
                "    INNER JOIN shoe_order AS so ON so.order_id = uo.order_id\n" +
                "WHERE u.role='client'\n" +
                "GROUP BY u.id\n" +
                "ORDER BY total_spent DESC;";
        // наступні чотири строчки не міняються
        try (Connection con = connectionManager.getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery(query)) {
                    while (rs.next()) {
                        usersList.add(mapBestUser(rs));// пишемо метод мапи для наших даних з бд, щоб вставити їх в наше ДТО
                        // приклад реалізації mapBestUser(rs) дивитись нижче, там пояснення
                    }
                    return usersList;
                    // на цьому все, ідемо в OrderService і OrderController шукаємо метод тест і змінюємо тип повертаємого об'єкта в List<?>.
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //Якщо треба вивести взуття
        /*List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM shoe")) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        // Якщо треба вивести замовлення
        /*List<Order> orders = new ArrayList<>();
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
                        for (Order o: orders) {
                            o.setAddress(getAddressByOrder(o.getId()));
                        }
                    }
                    return orders;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        // Якщо треба вивести юзера
        /*List<User> userList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * from user")) {
                    while (rs.next()) {
                        userList.add(mapUsers(rs));
                    }
                    return userList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

    }

    private BestUserDTO mapBestUser(ResultSet rs) throws SQLException {
        BestUserDTO bestUserDTO = new BestUserDTO();
        //заповнюємо поля нашого об'єкта, викликаємо сетери (bestUserDTO.setUser_id і тд)
        // в дужках сетера беремо значення з нашого Result set який наповнений даними з бд
        // тип даних в методі rs.get?("") визначається типом даних самого поля
        // (якщо встановлюємо ім'я, то викликаємо rs.getString і тд)
        // !! в дужках гетера вказуємо ім'я стовбця, з якого треба взяти дані
        // ім'я стовбця виведе бд, коли будеш перевіряти чи працює запит
        // !! якщо будуть різні ім'я в гетерє і в бд - НІХУЯ не спрацює :(
        bestUserDTO.setUser_id(rs.getLong("id"));
        bestUserDTO.setOrder_id(rs.getLong("order_id"));
        bestUserDTO.setName(rs.getString("name"));
        bestUserDTO.setSurname(rs.getString("surname"));
        bestUserDTO.setTotal_spent(rs.getBigDecimal("total_spent"));
        return bestUserDTO;
    }

    // Робота з адресою////////////////////////////
    private Address getAddressByOrder(Long id) {
        Address address = new Address();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT address_id FROM `order` WHERE id=?")) {
                int k = 0;
                ps.setLong(++k, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        address = findAddressById(rs.getLong(1));
                    }
                    return address;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Address findAddressById(long id) {
        Address address = new Address();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM address WHERE id=?")) {
                int k = 0;
                ps.setLong(++k, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        address = mapAddress(rs);
                    }
                    return address;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Address mapAddress(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getLong("id"));
        address.setCountry(rs.getString("country"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
        address.setHouseNumber(rs.getString("house_number"));
        address.setEntrance(rs.getInt("entrance"));
        address.setApartmentNumber(rs.getInt("apartment_number"));
        return address;
    }
    //////////////////////////////////////////////////

    // Робота з юзером
    private User mapUsers(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setName(rs.getString("name"));
        u.setSurname(rs.getString("surname"));
        u.setPassword(rs.getString("password"));
        u.setEmail(rs.getString("email"));
        u.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
        u.setPhoneNumber(rs.getString("phone_number"));
        return u;
    }
    ///////////////////////////////////////////////////

    // Робота з взуттям
    private Shoe mapShoes(ResultSet rs) throws SQLException {
        Shoe s = new Shoe();
        s.setId(rs.getInt("id"));
        s.setSize(rs.getBigDecimal("size"));
        s.setColor(rs.getString("color"));
        s.setSeason(Season.valueOf(rs.getString("season").toUpperCase()));
        s.setSex(Sex.valueOf(rs.getString("sex").toUpperCase()));
        s.setPrice(rs.getBigDecimal("actual_price"));
        s.setName(rs.getString("name"));
        s.setAmount(rs.getInt("amount"));
        s.setImageId(rs.getLong("image_id"));
        return s;
    }
    ///////////////////////////////////////////////////
}
