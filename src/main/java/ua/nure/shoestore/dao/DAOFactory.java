package ua.nure.shoestore.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.dao.EntityDAOImpl.UserDAOImpl;

@Component
public class DAOFactory {

    DAOConfig config;

    @Bean
    public UserDAO getUserDAOInstance(DAOConfig config){
        return new UserDAOImpl(config);
    }
}
