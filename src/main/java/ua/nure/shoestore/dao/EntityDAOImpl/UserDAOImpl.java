package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;
import java.sql.*;
import java.util.Properties;

public class UserDAOImpl implements UserDAO {
    //ROLE AUTOMATICALLY IS "CLIENT"
    private static final String ADD_USER = "INSERT INTO user (name, surname, password, email, phone_number) VALUES (?, ?, ?, ?, ?)";
    private static final String LOGIN_ATTEMPT = "SELECT * FROM user WHERE email=? AND password=?";
 //   private static final String LOAD_ALL = "SELECT * FROM user";
    private static final String UPDATE = "UPDATE user SET name=?, surname=?, email=?, password=?, address_id=?, phone_number=? WHERE user_id=?";
    private static final String UPDATE_ROLE = "UPDATE user SET role=? WHERE user_id=?";

    private final String url;
    private final Properties dbProps = new Properties();

    public User getUser(String email, String password) {
        User user = new User();
        try (Connection con = getConnection()) {
            try (PreparedStatement pstmt = con.prepareStatement(LOGIN_ATTEMPT,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    boolean completed = resultSet.first();
                    if (completed) {
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setName(resultSet.getString("name"));
                        user.setSurname(resultSet.getString("surname"));
                        user.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
                        user.setPhoneNumber(resultSet.getString("phone_number"));
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void add(User user) {
        try (Connection con = getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
                int k=0;
                ps.setString(++k, user.getName());
                ps.setString(++k, user.getSurname());
                ps.setString(++k, user.getPassword());
                ps.setString(++k, user.getEmail());
                ps.setString(++k, user.getPhoneNumber());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        user.setUser_id(keys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user, long address_id) {
        try (Connection con = getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(UPDATE)) {
                int k = 0;
                ps.setString(++k, user.getName());
                ps.setString(++k, user.getSurname());
                ps.setString(++k, user.getEmail());
                ps.setString(++k, user.getPassword());
                ps.setLong(++k, address_id);
                ps.setString(++k, user.getPhoneNumber());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateRole(long user_id, Role role) {
        try (Connection con = getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(UPDATE_ROLE)) {
                int k = 0;
                ps.setLong(++k, user_id);
                ps.setString(++k, role.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDAOImpl(DAOConfig config) {
        url = config.getUrl();
        dbProps.setProperty("user", config.getUser());
        dbProps.setProperty("password", config.getPassword());
    }

    private static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                // nothing
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return getConnection(true);
    }

    private Connection getConnection(boolean autoCommit) throws SQLException {
        Connection con = DriverManager.getConnection(url, dbProps);
        con.setAutoCommit(autoCommit);
        return con;
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                // nothing to do
            }
        }
    }
}
