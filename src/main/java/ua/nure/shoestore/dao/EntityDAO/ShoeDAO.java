package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.dao.CRUDRepository;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.util.List;

public interface ShoeDAO extends CRUDRepository<Shoe> {
    List<Shoe> getShoesByColor(String color);
    List<Shoe> getShoesBySize(BigDecimal size);
    List<Shoe> getShoesBySex(Sex sex);
    List<Shoe> getShoesAscendingPrice();
    List<Shoe> getShoesDescendingPrice();
    List<Shoe> searchShoes(String name);

    List<Shoe> showShoePage(String name);

    //розділити image
    Long addShoeWithImage(ShoeDTO shoeDTO, String imageName);
    String imageNameByImageId(Long shoeId);

    void updateShoeAmount(long shoeId, int amount);
}
