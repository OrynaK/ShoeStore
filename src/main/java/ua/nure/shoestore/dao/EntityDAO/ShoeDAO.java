package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.util.List;

public interface ShoeDAO {
    public List<Shoe> getAllShoes();
    public List<Shoe> getShoesByColor(String color);
    public List<Shoe> getShoesBySize(BigDecimal size);
    public List<Shoe> getShoesBySex(Sex sex);
}
