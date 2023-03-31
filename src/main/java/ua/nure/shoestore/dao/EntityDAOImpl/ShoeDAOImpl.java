package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.DAOConfig;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Season;
import ua.nure.shoestore.entity.enums.Sex;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class ShoeDAOImpl implements ShoeDAO {

    private static final String GET_ALL_SHOES="SELECT * FROM shoe";
    private final String url;
    private final Properties dbProps = new Properties();


    @Override
    public List<Shoe> getAllShoes() {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery(GET_ALL_SHOES)) {
                    while (rs.next()) {
                        shoeList.add(mapShoes(rs));
                    }
                    return shoeList;
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ShoeDAOImpl(DAOConfig config){
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

    private Shoe mapShoes(ResultSet rs) throws SQLException{
        Shoe s = new Shoe();
        s.setShoeId(rs.getInt("shoe_id"));
        s.setSize(rs.getBigDecimal("size"));
        s.setColor(rs.getString("color"));
        s.setSeason(Season.valueOf(rs.getString("season").toUpperCase()));
        s.setSex(Sex.valueOf(rs.getString("sex").toUpperCase()));
        s.setPrice(rs.getBigDecimal("price"));
        s.setName(rs.getString("name"));
        s.setAmount(rs.getInt("shoe_id"));
        s.setImageId(rs.getInt("shoe_id"));
        return s;
    }

}
