package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Season;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ShoeDAOImpl implements ShoeDAO {

    private static final String GET_ALL_SHOES = "SELECT * FROM shoe";
    private static final String GET_SHOES_ASCENDING_PRICE = "SELECT * FROM shoe ORDER BY actual_price";
    private static final String GET_SHOES_DESCENDING_PRICE = "SELECT * FROM shoe ORDER BY actual_price DESC";
    private static final String GET_SHOES_BY_COLOR = "SELECT * FROM shoe WHERE color=?";
    private static final String GET_SHOES_BY_SIZE = "SELECT * FROM shoe WHERE size=?";
    private static final String GET_SHOES_BY_SEX = "SELECT * FROM shoe WHERE sex=?";
    private static final String GET_SHOES_BY_NAME = "SELECT * FROM shoe WHERE name LIKE ?";

    private static final String ADD_SHOE = "INSERT INTO shoe (size, color, season, sex, actual_price, name, amount) VALUES(?,?,?,?,?,?,?)";

    private final ConnectionManager connectionManager;

    public ShoeDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public List<Shoe> findAll() {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery(GET_ALL_SHOES)) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Shoe findById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Shoe> getShoesByColor(String color) {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_SHOES_BY_COLOR)) {
                int k = 0;
                ps.setString(++k, color);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Shoe> getShoesBySize(BigDecimal size) {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_SHOES_BY_SIZE)) {
                int k = 0;
                ps.setBigDecimal(++k, size);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Shoe> getShoesBySex(Sex sex) {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_SHOES_BY_SEX)) {
                int k = 0;
                ps.setString(++k, sex.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Shoe> getShoesAscendingPrice() {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery(GET_SHOES_ASCENDING_PRICE)) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Shoe> getShoesDescendingPrice() {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery(GET_SHOES_DESCENDING_PRICE)) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Shoe> searchShoes(String name) {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_SHOES_BY_NAME)) {
                int k = 0;
                ps.setString(++k, "%" + name + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long insert(Shoe shoe) {
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(ADD_SHOE, Statement.RETURN_GENERATED_KEYS)) {
                int k = 0;
                ps.setBigDecimal(++k, shoe.getSize());
                ps.setString(++k, shoe.getColor());
                ps.setString(++k, shoe.getSeason().toString().toUpperCase()); // Використовуємо метод getSeason()
                ps.setString(++k, shoe.getSex().toString().toUpperCase()); // Використовуємо метод getSex()
                ps.setBigDecimal(++k, shoe.getPrice());
                ps.setString(++k, shoe.getName());
                ps.setInt(++k, shoe.getAmount());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        shoe.setId(keys.getLong(1));
                    }
                }
            }
            return shoe.getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Shoe entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    private Shoe mapShoes(ResultSet rs) throws SQLException {
        Shoe s = new Shoe();
        s.setId(rs.getInt("id"));
        s.setSize(rs.getBigDecimal("size"));
        s.setColor(rs.getString("color"));
        s.setSeason(Season.valueOf(rs.getString("season").toUpperCase()));
        s.setSex(Sex.valueOf(rs.getString("sex").toUpperCase()));
        s.setPrice(rs.getBigDecimal("actual_price"));
        s.setName(rs.getString("name"));
        s.setAmount(rs.getInt("id"));
        s.setImageId(rs.getInt("image_id"));
        return s;
    }

}
