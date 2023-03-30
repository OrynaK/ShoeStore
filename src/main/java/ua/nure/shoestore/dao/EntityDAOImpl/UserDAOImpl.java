package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;

import javax.security.auth.login.LoginException;
import java.sql.*;
import java.util.Properties;

public class UserDAOImpl implements UserDAO {
    //ROLE AUTOMATICALLY IS "CLIENT"
    private static final String ADD_USER="INSERT INTO user (name, surname, password, email, phone_number) VALUES (?, ?, ?, ?, ?)";
    private static final String LOGIN_ATTEMPT="SELECT user_id, name, surname, role, blocked, address_id FROM user WHERE email=? AND password=?";
    private final String url;
    private final Properties dbProps = new Properties();

    public void addUser(User user){
        try(Connection con=getConnection()) {
            try(PreparedStatement ps=con.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, user.getName());
                ps.setString(2, user.getSurname());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getPhoneNumber());
                ps.executeUpdate();
                try(ResultSet keys = ps.getGeneratedKeys()){
                    if(keys.next()){
                        user.setUser_id(keys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String email, String password){
        User user = new User();
        try(Connection con = getConnection()) {
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
                        user.setAddress_id(resultSet.getLong("address_id"));
                        user.setBlocked(false);
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

    public UserDAOImpl(DAOConfig config){
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
