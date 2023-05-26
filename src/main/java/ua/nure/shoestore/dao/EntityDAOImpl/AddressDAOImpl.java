package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.AddressDAO;
import ua.nure.shoestore.entity.Address;

import java.sql.*;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {
    ConnectionManager connectionManager;
    private static final String ADD_ADDRESS = "INSERT INTO address (country, city, street, house_number, entrance, apartment_number) values (?, ?, ?, ?, ?, ?)";
    private static final String GET_ADDRESS_BY_ID = "SELECT * FROM address WHERE id=?";

    private static final String GET_ADDRESS_BY_ORDER="SELECT address_id FROM `order` WHERE id=?";

    public long insert(Address address) {
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(ADD_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
                int k = 0;
                ps.setString(++k, address.getCountry());
                ps.setString(++k, address.getCity());
                ps.setString(++k, address.getStreet());
                ps.setString(++k, address.getHouseNumber());
                ps.setInt(++k, address.getEntrance());
                ps.setInt(++k, address.getApartmentNumber());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        address.setId(keys.getLong(1));
                    }
                }
                return address.getId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Address address) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Address> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    public Address findById(long id) {
        Address address = new Address();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_ADDRESS_BY_ID)) {
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

    public Address mapAddress(ResultSet rs) throws SQLException {
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

    public AddressDAOImpl(DAOConfig config) {
        connectionManager = new ConnectionManager(config);
    }

    @Override
    public Address getAddressByOrder(Long id) {
        Address address = new Address();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_ADDRESS_BY_ORDER)) {
                int k = 0;
                ps.setLong(++k, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        address = findById(rs.getLong(1));
                    }
                    return address;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
