package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.CartDAO;
import ua.nure.shoestore.entity.Cart;
import ua.nure.shoestore.entity.ShoeOrder;

import java.sql.*;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    private static final String INSERT_CART = "INSERT INTO cart (client_id) VALUES (?)";
    private static final String INSERT_CART_SHOE = "INSERT INTO cart_shoe (cart_id, shoe_id,price, amount) VALUES (?, ?, ?, ?)";
    private static final String GET_CART_BY_CLIENT_ID = "SELECT * FROM cart WHERE client_id = ?";
    private static final String GET_CART_SHOE_BY_USER_ID = "SELECT shoe_id, price, amount FROM cart_shoe WHERE cart_id IN (SELECT id FROM cart WHERE client_id = ?)";
    ConnectionManager connectionManager;

    public CartDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public long insert(Cart cart) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection(false);
            statement = connection.prepareStatement(INSERT_CART, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                cart.setId(resultSet.getLong(1));
                statement = connection.prepareStatement(INSERT_CART_SHOE);
                for (ShoeOrder shoeOrder : cart.getShoesInCart()) {
                    setCartOrder(cart, statement, shoeOrder);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
            return cart.getId();
        } catch (SQLException e) {
            ConnectionManager.rollback(connection);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(connection, statement);
        }
    }

    private static void setCartOrder(Cart cart, PreparedStatement statement, ShoeOrder shoeOrder) throws SQLException {
        int k = 0;
        statement.setLong(++k, cart.getId());
        statement.setLong(++k, shoeOrder.getShoeId());
        statement.setBigDecimal(++k, shoeOrder.getPrice());
        statement.setInt(++k, shoeOrder.getAmount());
    }

    @Override
    public Cart findByUserId(long userId) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CART_BY_CLIENT_ID)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Cart cart = null;
            if (resultSet.next()) {
                cart = mapCart(resultSet);
                try (PreparedStatement statement1 = connection.prepareStatement(GET_CART_SHOE_BY_USER_ID)) {
                    statement1.setLong(1, userId);
                    ResultSet resultSet1 = statement1.executeQuery();
                    while (resultSet1.next()) {
                        ShoeOrder shoeOrder = mapShoeCart(resultSet1);
                        cart.addShoe(shoeOrder);
                    }
                }
            }
            return cart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Cart mapCart(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId(resultSet.getLong("id"));
        cart.setUserId(resultSet.getLong("client_id"));
        return cart;
    }

    private static ShoeOrder mapShoeCart(ResultSet resultSet1) throws SQLException {
        ShoeOrder shoeOrder = new ShoeOrder();
        shoeOrder.setShoeId(resultSet1.getLong("shoe_id"));
        shoeOrder.setPrice(resultSet1.getBigDecimal("price"));
        shoeOrder.setAmount(resultSet1.getInt("amount"));
        return shoeOrder;
    }

/*    public static void main(String[] args) {
        // Test
        CartDAOImpl cartDAO = new CartDAOImpl(new DAOConfig("MySQL", "jdbc:mysql://localhost:3306/shoe_store?sslMode=DISABLED&serverTimezone=UTC", "root", "root"));
        Cart cart = new Cart();
        *//*cart.setUserId(9);
        ShoeOrder shoeOrder = new ShoeOrder();
        shoeOrder.setShoeId(1);
        shoeOrder.setPrice(new BigDecimal(1000));
        shoeOrder.setAmount(1);
        cart.getShoesInCart().add(shoeOrder);
        cartDAO.insert(cart);*//*
        System.out.println(cartDAO.findByUserId(13));
    }*/

    @Override
    public void update(Cart entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Cart> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cart findById(long id) {
        throw new UnsupportedOperationException();
    }
}
