package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.util.List;

public interface ShoeDAO {
    List<Shoe> getAllShoes();
    List<Shoe> getShoesByColor(String color);
    List<Shoe> getShoesBySize(BigDecimal size);
    List<Shoe> getShoesBySex(Sex sex);
    List<Shoe> getShoesAscendingPrice();
    List<Shoe> getShoesDescendingPrice();
    List<Shoe> searchShoes(String name);
    void add(Shoe shoe);

}
