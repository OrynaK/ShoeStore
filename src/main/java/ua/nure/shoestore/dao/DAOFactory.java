package ua.nure.shoestore.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ua.nure.shoestore.dao.EntityDAO.AddressDAO;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.dao.EntityDAOImpl.AddressDAOImpl;
import ua.nure.shoestore.dao.EntityDAOImpl.OrderDAOImpl;
import ua.nure.shoestore.dao.EntityDAOImpl.ShoeDAOImpl;
import ua.nure.shoestore.dao.EntityDAOImpl.UserDAOImpl;

@Component
public class DAOFactory {

    DAOConfig config;

    @Bean
    public UserDAO getUserDAOInstance(DAOConfig config){
        return new UserDAOImpl(config);
    }
    @Bean
    public AddressDAO getAddressDAOInstance(DAOConfig config){
        return new AddressDAOImpl(config);
    }
    @Bean
    public ShoeDAO getShoeDAOInstance(DAOConfig config) {
        return new ShoeDAOImpl(config);
    }

    @Bean
    public OrderDAO getOrderDAOInstance(DAOConfig config) {
        return new OrderDAOImpl(config);
    }
}
