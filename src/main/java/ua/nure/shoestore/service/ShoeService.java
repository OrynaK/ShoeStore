package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.entity.Shoe;

import java.util.List;

@Service
public class ShoeService {
   private ShoeDAO shoeDAO;

   public ShoeService(ShoeDAO shoeDAO){this.shoeDAO = shoeDAO;}

    public List<Shoe> getShoes(){
       return shoeDAO.getAllShoes();
    }
}
