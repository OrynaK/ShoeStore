package ua.nure.shoestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ua.nure.shoestore.dao.DAOConfig;

@SpringBootApplication
@EnableConfigurationProperties(DAOConfig.class)
public class ShoeStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoeStoreApplication.class, args);
    }

}
