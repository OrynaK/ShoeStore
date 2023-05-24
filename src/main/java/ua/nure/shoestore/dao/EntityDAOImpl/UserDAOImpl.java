package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.forms.UpdateForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    //ROLE AUTOMATICALLY IS "CLIENT"
    private static final String ADD_USER = "INSERT INTO user (name, surname, password, email, phone_number) VALUES (?, ?, ?, ?, ?)";
    private static final String LOGIN_ATTEMPT = "SELECT * FROM user WHERE email=? AND password=?";
    private static final String UPDATE = "UPDATE user SET name=?, surname=?, email=?, password=?, phone_number=? WHERE id=?";
    private static final String UPDATE_ROLE = "UPDATE user SET role=? WHERE id=?";
    private static final String GET_ALL_USERS = "SELECT * from user";
    private static final String GET_USER_BY_ID = "SELECT * from user WHERE id=?";
    private final ConnectionManager connectionManager;

    public UserDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery(GET_ALL_USERS)) {
                    while (rs.next()) {
                        userList.add(mapUsers(rs));
                    }
                    return userList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(long id) {
        User user = new User();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_USER_BY_ID)) {
                int k = 0;
                ps.setLong(++k, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        user = mapUsers(resultSet);
                    }
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public User getUser(String email, String password) {
        User user = new User();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement pstmt = con.prepareStatement(LOGIN_ATTEMPT,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    boolean completed = resultSet.first();
                    if (completed) {
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setId(resultSet.getInt("id"));
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
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
                int k = 0;
                ps.setString(++k, user.getName());
                ps.setString(++k, user.getSurname());
                ps.setString(++k, user.getPassword());
                ps.setString(++k, user.getEmail());
                ps.setString(++k, user.getPhoneNumber());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        user.setId(keys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User update(UpdateForm updateForm) {
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(UPDATE)) {
                int k = 0;
                ps.setString(++k, updateForm.getName());
                ps.setString(++k, updateForm.getSurname());
                ps.setString(++k, updateForm.getEmail());
                ps.setString(++k, updateForm.getPassword());
                ps.setString(++k, updateForm.getPhoneNumber());
                ps.setLong(++k, updateForm.getId());
                ps.executeUpdate();
            }
            return getUser(updateForm.getEmail(), updateForm.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRole(long userId, Role role) {
        try (Connection con = connectionManager.getConnection()) {
            User user = getUserById(userId);
            if (user.getRole() == Role.ADMIN) {
                throw new IllegalArgumentException("Admins cannot update admins");
            }
            try (PreparedStatement ps = con.prepareStatement(UPDATE_ROLE)) {
                int k = 0;
                ps.setString(++k, role.toString().toLowerCase());
                ps.setLong(++k, userId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
