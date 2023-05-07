package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.ConnectionManager;
import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.AddressDAO;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.Shoe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AddressDAOImpl implements AddressDAO {
    ConnectionManager connectionManager;
    private static final String ADD_ADDRESS = "INSERT INTO address (country, city, street, house_number, entrance, apartment_number) values (?, ?, ?, ?, ?, ?)";
    private static final String GET_ADDRESS_BY_ID = "SELECT * FROM address WHERE address_id=?";



    public long add(Address address) {
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
                        address.setAddressId(keys.getLong(1));
                    }
                }
                return address.getAddressId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Address getById(long id) {
        Address address = new Address();
        try (Connection con = connectionManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(GET_ADDRESS_BY_ID)) {
                int k = 0;
                ps.setLong(++k, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        address=mapAddress(rs);
                    }
                    return address;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Address mapAddress(ResultSet rs) throws SQLException {
        Address address=new Address();
        address.setAddressId(rs.getLong("address_id"));
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
}
