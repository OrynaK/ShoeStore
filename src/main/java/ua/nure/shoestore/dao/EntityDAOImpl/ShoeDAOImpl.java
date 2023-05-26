package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.dto.ShoeDTO;
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

    private static final String GET_IMAGE_BY_SHOE_ID = "SELECT name FROM image WHERE image.id = ?";

    private static final String ADD_SHOE = "INSERT INTO shoe (size, color, season, sex, actual_price, name, amount) VALUES(?,?,?,?,?,?,?)";
    private static final String ADD_SHOE_WITH_IMAGE = "INSERT INTO shoe (name, size, color, image_id, amount, actual_price, season, sex) VALUES(?,?,?,?,?,?,?,?)";

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
        try( Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM shoe WHERE id = ?")) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapShoes(rs);
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

    @Override
    public List<Shoe> showShoePage(String name) {
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

    @Override
    public Long addShoeWithImage(ShoeDTO shoeDTO, String imageName) {
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps1 = con.prepareStatement("INSERT INTO image (name, path) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {
                int k = 0;
                ps1.setString(++k, imageName);
                ps1.setString(++k, shoeDTO.getImagePath());
                ps1.executeUpdate();
                try (ResultSet key1 = ps1.getGeneratedKeys()) {
                    if (key1.next()) {
                        Long imageId = key1.getLong(1);
                        try (PreparedStatement ps2 = con.prepareStatement(ADD_SHOE_WITH_IMAGE, Statement.RETURN_GENERATED_KEYS)) {
                            int j = 0;
                            ps2.setString(++j, shoeDTO.getName());
                            ps2.setBigDecimal(++j, shoeDTO.getSize());
                            ps2.setString(++k, shoeDTO.getColor());
                            ps2.setLong(++k, imageId);
                            ps2.setInt(++k, shoeDTO.getAmount());
                            ps2.setBigDecimal(++k, shoeDTO.getPrice());
                            ps2.setString(++k, shoeDTO.getSeason().toString().toUpperCase());
                            ps2.setString(++k, shoeDTO.getSex().toString().toUpperCase());
                            ps2.executeUpdate();
                            try (ResultSet key2 = ps1.getGeneratedKeys()) {
                                if (key2.next()) {
                                    return key2.getLong(1);
                                }
                            }
                        }
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String imageNameByImageId(Long shoeId) {
        String name;
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_IMAGE_BY_SHOE_ID)) {
                int k = 0;
                ps.setLong(++k, shoeId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        name = rs.getString("name");
                        return name;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
        s.setAmount(rs.getInt("amount"));
        s.setImageId(rs.getLong("image_id"));
        return s;
    }

}
