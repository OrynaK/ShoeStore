package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShoeService {
   private ShoeDAO shoeDAO;

   public ShoeService(ShoeDAO shoeDAO){this.shoeDAO = shoeDAO;}

    public List<Shoe> getShoes(){
       return shoeDAO.getAllShoes();
    }
    public List<Shoe> getShoesByColor(String color){
       return shoeDAO.getShoesByColor(color);
    }
    public List<Shoe> getShoesBySize(BigDecimal size){
       return shoeDAO.getShoesBySize(size);
    }
    public List<Shoe> getShoesBySex(Sex sex){
       return shoeDAO.getShoesBySex(sex);
    }
}
