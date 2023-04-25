package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.AddressDAO;
import ua.nure.shoestore.entity.Address;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class AddressDAOImpl implements AddressDAO {

    private static final String ADD_ADDRESS="INSERT INTO address (country, city, street, house_number, entrance, apartment_number) values (?, ?, ?, ?, ?, ?)";

    private final String url;
    private final Properties dbProps = new Properties();


    public long add(Address address) {
        try(Connection con= getConnection()){
            try(PreparedStatement ps=con.prepareStatement(ADD_ADDRESS, Statement.RETURN_GENERATED_KEYS)){
                int k=0;
                ps.setString(++k,address.getCountry());
                ps.setString(++k,address.getCity());
                ps.setString(++k,address.getStreet());
                ps.setString(++k,address.getHouseNumber());
                ps.setInt(++k,address.getEntrance());
                ps.setInt(++k,address.getApartmentNumber());
                ps.executeUpdate();
                try(ResultSet keys=ps.getGeneratedKeys()){
                    if(keys.next()){
                        address.setAddressId(keys.getLong(1));
                    }
                }
                return address.getAddressId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AddressDAOImpl(DAOConfig config){
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
