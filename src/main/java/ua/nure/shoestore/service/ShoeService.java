package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.cards.ShoeCard;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoeService {
   private ShoeDAO shoeDAO;

   public ShoeService(ShoeDAO shoeDAO){this.shoeDAO = shoeDAO;}

    public List<ShoeCard> getShoes(){
       List<ShoeCard> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getAllShoes()) {
            ShoeCard shoeCard = new ShoeCard(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
       return shoeCards;
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
    public List<Shoe> getShoesAscendingPrice(){
        return shoeDAO.getShoesAscendingPrice();
    }
    public List<Shoe> getShoesDescendingPrice(){
        return shoeDAO.getShoesDescendingPrice();
    }
    public List<Shoe> searchShoes(String name){
        return shoeDAO.searchShoes(name);
    }
    public void addShoe(Shoe shoe) {
        shoeDAO.add(shoe);
    }

}
