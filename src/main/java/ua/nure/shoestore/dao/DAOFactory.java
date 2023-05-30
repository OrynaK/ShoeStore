package ua.nure.shoestore.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.nure.shoestore.dao.EntityDAO.*;
import ua.nure.shoestore.dao.EntityDAOImpl.*;

@Component
public class DAOFactory {

    DAOConfig config;

    @Bean
    public UserDAO getUserDAOInstance(DAOConfig config) {
        return new UserDAOImpl(config);
    }

    @Bean
    public AddressDAO getAddressDAOInstance(DAOConfig config) {
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

    @Bean
    public CartDAO getCartDAOInstance(DAOConfig config) {
        return new CartDAOImpl(config);
    }
    @Bean
    public WorkerDAO getWorkerDAOInstance(DAOConfig config) {
        return new WorkerDAOImpl(config);
    }
}
